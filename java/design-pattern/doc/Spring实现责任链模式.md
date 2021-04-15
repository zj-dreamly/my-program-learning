关于责任链模式，其有两种形式，一种是通过外部调用的方式对链的各个节点调用进行控制，从而进行链的各个节点之间的切换；另一种是链的每个节点自由控制是否继续往下传递链的进度，这种比较典型的使用方式就是Netty中的责任链模式。本文主要讲解我们如何在Spring中使用这两种责任链模式。

### 1. 外部控制模式

​    对于外部控制的方式，这种方式比较简单，链的每个节点只需要专注于各自的逻辑即可，而当前节点调用完成之后是否继续调用下一个节点，这个则由外部控制逻辑进行。这里我们以一个过滤器的实现逻辑为例进行讲解，在平常工作中，我们经常需要根据一系列的条件对某个东西进行过滤，比如任务服务的设计，在执行某个任务时，其需要经过诸如时效性检验，风控拦截，任务完成次数等过滤条件的检验之后才能判断当前任务是否能够执行，只有在所有的过滤条件都完成之后，我们才能执行该任务。那么这里我们就可以抽象出一个`Filter`接口，其设计如下：

```java
public interface Filter {

  /**
   * 用于对各个任务节点进行过滤
   */
  boolean filter(Task task);

}
```

​    这里的`Filter.filter()`方法只有一个参数`Task`，主要就是控制当前task是否需要被过滤掉，其有一个boolean类型的返回值，通过该返回值以告知外部控制逻辑是否需要将该task过滤掉。对于该接口的子类，我们只需要将其声明为Spring所管理的一个bean即可：

```java
// 时效性检验
@Component
public class DurationFilter implements Filter {

  @Override
  public boolean filter(Task task) {
    System.out.println("时效性检验");
    return true;
  }
}
// 风控拦截
@Component
public class RiskFilter implements Filter {

  @Override
  public boolean filter(Task task) {
    System.out.println("风控拦截");
    return true;
  }
}
// 次数限制校验
@Component
public class TimesFilter implements Filter {

  @Override
  public boolean filter(Task task) {
    System.out.println("次数限制检验");
    return true;
  }
}
```

​    上面我们模拟声明了三个`Filter`的子类，用于设计一系列的控制当前task是否需要被过滤的逻辑，结构上的逻辑其实比较简单，主要就是需要将其声明为Spring所管理的一个bean。下面是我们的控制逻辑:

```java
@Service
public class ApplicationService {

  @Autowired
  private List<Filter> filters;

  public void mockedClient() {
    Task task = new Task(); // 这里task一般是通过数据库查询得到的
    for (Filter filter : filters) {
      if (!filter.filter(task)) {
        return;
      }
    }

    // 过滤完成，后续是执行任务的逻辑
  }
}
```

​    在上述的控制逻辑中，对于过滤器的获取，只需要通过Spring的自动注入即可，这里注入的是一个`List<Filter>`，也就是说，如果我们有新的`Filter`实例需要参与责任链的过滤，只需要将其声明为一个Spring容器所管理的bean即可。

​    这种责任链设计方式的优点在于链的控制比较简单，只需要实现一个统一的接口即可，其基本上能够满足大部分的逻辑控制，但是对于某些需要动态调整链的需求其就无能为力了。比如在执行到某个节点之后需要动态的判断是否执行下一个节点，或者说要执行某些分叉的节点等等。这个时候我们就需要将链节点的传递工作交由各个节点进行。

### 2. 节点控制模式

​    对于节点控制调用的方式，其主要有三个控制点：Handler，HandlerContext和Pipeline。Handler中是用于编写具体的业务代码的；HandlerContext则主要是用于对Handler进行包裹，并且用于控制进行下一个节点的调用的；Pipeline则主要是用于控制整体的流程调用的，比如对于任务的执行，其有任务的查询，任务的过滤和执行任务等等流程，这些流程整体的逻辑控制就是由Pipeline来控制的，在每个流程中又包含了一系列的子流程，这些子流程则是由一个个的HandlerContext和Handler进行梳理的。这种责任链的控制方式整体逻辑如下图所示：

![责任链模式](https://oscimg.oschina.net/oscnet/b6f7e082ead90ad78383d977c551234549d.jpg)

​    从图中可以看出，我们将整个流程通过`Pipeline`对象进行了抽象，这里主要分为了三个步骤：查询task，过滤task和执行task。在每个步骤中，我们都使用了一系列的链式调用。图中需要注意的是，在每次调用链的下一个节点的时候，我们都是通过具体的Handler进行的，也就是说是否进行链的下一个节点的调用，我们是通过业务实现方来进行动态控制的。

​    关于该模式的设计，我们首先需要强调的就是`Handler`接口的设计，其设计如下所示：

```java
public interface Handler {

  /**
   * 处理接收到前端请求的逻辑
   */
  default void receiveTask(HandlerContext ctx, Request request) {
    ctx.fireTaskReceived(request);
  }

  /**
   * 查询到task之后，进行task过滤的逻辑
   */
  default void filterTask(HandlerContext ctx, Task task) {
    ctx.fireTaskFiltered(task);
  }

  /**
   * task过滤完成之后，处理执行task的逻辑
   */
  default void executeTask(HandlerContext ctx, Task task) {
    ctx.fireTaskExecuted(task);
  }

  /**
   * 当实现的前面的方法抛出异常时，将使用当前方法进行异常处理，这样可以将每个handler的异常
   * 都只在该handler内进行处理，而无需额外进行捕获
   */
  default void exceptionCaught(HandlerContext ctx, Throwable e) {
    throw new RuntimeException(e);
  }

  /**
   * 在整个流程中，保证最后一定会执行的代码，主要是用于一些清理工作
   */
  default void afterCompletion(HandlerContext ctx) {
    ctx.fireAfterCompletion(ctx);
  }
}
```

​    这里的`Handler`接口主要是对具体的业务逻辑的一个抽象，对于该`Handler`主要有如下几点需要说明：

- 在前面图中`Pipeline`的每个层级中对应于该`Handler`都有一个方法，在需要进行具体的业务处理的时候，用户只需要声明一个bean，具体实现某个当前业务所需要处理的层级的方法即可，而无需管其他的逻辑；
- 每个层级的方法中，第一个参数都是一个`HandlerContext`类型的，该参数主要是用于进行流程控制的，比如是否需要将当前层级的调用链往下继续传递，这里链的传递工作主要是通过`ctx.fireXXX()`方法进行的；
- 每个层级的方法都有默认实现，默认实现方式就是将链的调用继续往下进行传递；
- 每个`Handler`中都有一个`exceptionCaught()`方法和`afterCompletion()`方法，这两个方法分别用于异常控制和所有调用完成后的清理的，这里的异常控制主要是捕获当前`Handler`中的异常，而`afterCompletion()`方法则会保证在所有步骤之后一定会进行调用的，无论是否抛出异常；
- 对于`Handler`的使用，我们希望能够达到的目的是，适用方只需要实现该接口，并且使用某个注解来将其标志为`Spring`的bean即可，而无需管整个`Pipeline`的组装和流程控制。通过这种方式，我们即保留了每个Spring提供给我们的便利性，也使用了`Pipeline`模式的灵活性。

​    上述流程代码中，我们注意到，每个层级的方法中都有一个`HandlerContext`用于传递链相关的控制信息，这里我们来看一下其源码：

```java
@Component
@Scope("prototype")
public class HandlerContext {

  HandlerContext prev;
  HandlerContext next;
  Handler handler;

  private Task task;

  public void fireTaskReceived(Request request) {
    invokeTaskReceived(next(), request);
  }

  /**
   * 处理接收到任务的事件
   */
  static void invokeTaskReceived(HandlerContext ctx, Request request) {
    if (ctx != null) {
      try {
        ctx.handler().receiveTask(ctx, request);
      } catch (Throwable e) {
        ctx.handler().exceptionCaught(ctx, e);
      }
    }
  }

  public void fireTaskFiltered(Task task) {
    invokeTaskFiltered(next(), task);
  }

  /**
   * 处理任务过滤事件
   */
  static void invokeTaskFiltered(HandlerContext ctx, Task task) {
    if (null != ctx) {
      try {
        ctx.handler().filterTask(ctx, task);
      } catch (Throwable e) {
        ctx.handler().exceptionCaught(ctx, e);
      }
    }
  }

  public void fireTaskExecuted(Task task) {
    invokeTaskExecuted(next(), task);
  }

  /**
   * 处理执行任务事件
   */
  static void invokeTaskExecuted(HandlerContext ctx, Task task) {
    if (null != ctx) {
      try {
        ctx.handler().executeTask(ctx, task);
      } catch (Exception e) {
        ctx.handler().exceptionCaught(ctx, e);
      }
    }
  }

  public void fireAfterCompletion(HandlerContext ctx) {
    invokeAfterCompletion(next());
  }

  static void invokeAfterCompletion(HandlerContext ctx) {
    if (null != ctx) {
      ctx.handler().afterCompletion(ctx);
    }
  }

  private HandlerContext next() {
    return next;
  }

  private Handler handler() {
    return handler;
  }
}
```

​    在`HandlerContext`中，我们需要说明如下几点：

- 之前`Handler`接口默认实现的`ctx.fireXXX()`方法，在这里都委托给了对应的`invokeXXX()`方法进行调用，而且我们需要注意到，在传递给`invokeXXX()`方法的参数里，传入的`HandlerContext`对象都是通过`next()`方法获取到的。也就是说我们在`Handler`中调用`ctx.fireXXX()`方法时，都是在调用当前handler的下一个handler对应层级的方法，通过这种方式我们就实现了链的往下传递。
- 在上一点中我们说到，在某个`Handler`中如果想让链往下传递，只需要调用`ctx.fireXXX()`方法即可，也就是说，如果我们在某个`Handler`中，如果根据业务，当前层级已经调用完成，而无需调用后续的`Handler`，那么我们就不需要调用`ctx.fireXXX()`方法即可；
- 在`HandlerContext`中，我们也实现了`invokeXXX()`方法，该方法的主要作用是供给外部的`Pipeline`进行调用的，以开启每个层级的链；
- 在每个`invokeXXX()`方法中，我们都使用try…catch将当前层级的调用抛出的异常给捕获了，然后调用`ctx.handler().exceptionCaught()`方法处理该异常，这也就是我们前面说的，如果想处理当前`Handler`中的异常，只需要实现该`Handler`中的`exceptionCaught()`方法即可，异常捕获流程就是在这里的`HandlerContext`中进行处理的；
- 在`HandlerContext`的声明处，我们需要注意到，其使用了`@Component`和`@Scope("prototype")`注解进行标注了，这说明我们的`HandlerContext`是由Spring所管理的一个bean，并且由于我们每一个`Handler`实际上都由一个`HandlerContext`维护着，所以这里必须声明为`prototype`类型。通过这种方式，我们的`HandlerContext`也就具备了诸如Spring相关的bean的功能，也就能够根据业务需求进行一些额外的处理了；

​    前面我们讲解了`Handler`和`HandlerContext`的具体实现，以及实现的过程中需要注意的问题，下面我们就来看一下进行流程控制的`Pipeline`是如何实现的，如下是`Pipeline`接口的定义：

```java
public interface Pipeline {
  
  Pipeline fireTaskReceived();
  
  Pipeline fireTaskFiltered();
  
  Pipeline fireTaskExecuted();
  
  Pipeline fireAfterCompletion();
}
```

​    这里 主要是定义了一个`Pipeline`接口，该接口定义了一系列的层级调用，是每个层级的入口方法。如下是该接口的一个实现类：

```java
@Component("pipeline")
@Scope("prototype")
public class DefaultPipeline implements Pipeline, ApplicationContextAware, InitializingBean {
  // 创建一个默认的handler，将其注入到首尾两个节点的HandlerContext中，其作用只是将链往下传递
  private static final Handler DEFAULT_HANDLER = new Handler() {};

  // 将ApplicationContext注入进来的主要原因在于，HandlerContext是prototype类型的，因而需要
  // 通过ApplicationContext.getBean()方法来获取其实例
  private ApplicationContext context;

  // 创建一个头结点和尾节点，这两个节点内部没有做任何处理，只是默认的将每一层级的链往下传递，
  // 这里头结点和尾节点的主要作用就是用于标志整个链的首尾，所有的业务节点都在这两个节点中间
  private HandlerContext head;
  private HandlerContext tail;

  // 用于业务调用的request对象，其内部封装了业务数据
  private Request request;
  // 用于执行任务的task对象
  private Task task;

  // 最初始的业务数据需要通过构造函数传入，因为这是驱动整个pipeline所需要的数据，
  // 一般通过外部调用方的参数进行封装即可
  public DefaultPipeline(Request request) {
    this.request = request;
  }

  // 这里我们可以看到，每一层级的调用都是通过HandlerContext.invokeXXX(head)的方式进行的，
  // 也就是说我们每一层级链的入口都是从头结点开始的，当然在某些情况下，我们也需要从尾节点开始链
  // 的调用，这个时候传入tail即可。
  @Override
  public Pipeline fireTaskReceived() {
    HandlerContext.invokeTaskReceived(head, request);
    return this;
  }

  // 触发任务过滤的链调用
  @Override
  public Pipeline fireTaskFiltered() {
    HandlerContext.invokeTaskFiltered(head, task);
    return this;
  }

  // 触发任务执行的链执行
  @Override
  public Pipeline fireTaskExecuted() {
    HandlerContext.invokeTaskExecuted(head, task);
    return this;
  }

  // 触发最终完成的链的执行
  @Override
  public Pipeline fireAfterCompletion() {
    HandlerContext.invokeAfterCompletion(head);
    return this;
  }
  
  // 用于往Pipeline中添加节点的方法，读者朋友也可以实现其他的方法用于进行链的维护
  void addLast(Handler handler) {
    HandlerContext handlerContext = newContext(handler);
    tail.prev.next = handlerContext;
    handlerContext.prev = tail.prev;
    handlerContext.next = tail;
    tail.prev = handlerContext;
  }

  // 这里通过实现InitializingBean接口来达到初始化Pipeline的目的，可以看到，这里初始的时候
  // 我们通过ApplicationContext实例化了两个HandlerContext对象，然后将head.next指向tail节点，
  // 将tail.prev指向head节点。也就是说，初始时，整个链只有头结点和尾节点。
  @Override
  public void afterPropertiesSet() throws Exception {
    head = newContext(DEFAULT_HANDLER);
    tail = newContext(DEFAULT_HANDLER);
    head.next = tail;
    tail.prev = head;
  }

  // 使用默认的Handler初始化一个HandlerContext
  private HandlerContext newContext(Handler handler) {
    HandlerContext context = this.context.getBean(HandlerContext.class);
    context.handler = handler;
    return context;
  }

  // 注入ApplicationContext对象
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.context = applicationContext;
  }
}
```

​    关于`DefaultPipeline`的实现，主要有如下几点需要说明：

- `DefaultPipeline`使用`@Component`和`@Scope("prototype")`注解进行了标注，前一个注解用于将其声明为一个Spring容器所管理的bean，而后一个注解则用于表征`DefaultPipeline`是一个多例类型的，很明显，这里的`Pipeline`是有状态的。这里需要进行说明的是，"有状态"主要是因为我们可能会根据业务情况动态的调整个链的节点情况，而且这里的`Request`和`Task`对象都是与具体的业务相关的，因而必须声明为`prototype`类型；
- 上面的示例中，`Request`对象是通过构造`Pipeline`对象的时候传进来的，而`Task`对象则是在`Pipeline`的流转过程中生成的，这里比如通过完成`fireTaskReceived()`链的调用之后，就需要通过外部请求`Request`得到一个`Task`对象，从而进行整个`Pipeline`的后续处理；

​    这里我们已经实现了`Pipeline`，`HandlerContext`和`Handler`，知道这些bean都是被Spring所管理的bean，那么我们接下来的问题主要在于如何进行整个链的组装。这里的组装方式比较简单，其主要需要解决两个问题：

- 对于后续写业务代码的人而言，其只需要实现一个`Handler`接口即可，而无需处理与链相关的所有逻辑，因而我们需要获取到所有实现了`Handler`接口的bean；
- 将实现了`Handler`接口的bean通过`HandlerContext`进行封装，然后将其添加到`Pipeline`中。

​    这里的第一个问题比较好处理，因为通过ApplicationContext就可以获取实现了某个接口的所有bean，而第二个问题我们可以通过声明一个实现了BeanPostProcessor接口的类来实现。如下是其实现代码：

```java
@Component
public class HandlerBeanProcessor implements BeanPostProcessor, ApplicationContextAware {

  private ApplicationContext context;

  // 该方法会在一个bean初始化完成后调用，这里主要是在Pipeline初始化完成之后获取所有实现了
  // Handler接口的bean，然后通过调用Pipeline.addLast()方法将其添加到pipeline中
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof DefaultPipeline) {
      DefaultPipeline pipeline = (DefaultPipeline) bean;
      Map<String, Handler> handlerMap = context.getBeansOfType(Handler.class);
      handlerMap.forEach((name, handler) -> pipeline.addLast(handler));
    }

    return bean;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.context = applicationContext;
  }
}
```

​    这里我们整个链的维护工作就已经完成，可以看到，现在基本上已经实现了前面图中整个链式流程的控制。这里需要说明的一点是，上面的`HandlerBeanProcessor.postProcessAfterInitialization()`方法的执行是在`InitializingBean.afterPropertySet()`方法之后执行的，也就是说这里`HandlerBeanProcessor`在执行时，整个`Pipeline`是已经初始化完成了的。下面我们来看一下外部客户端如何进行整个链是流程的控制：

```java
@Service
public class ApplicationService {

  @Autowired
  private ApplicationContext context;
  
  public void mockedClient() {
    Request request = new Request();  // request一般是通过外部调用获取
    Pipeline pipeline = newPipeline(request);
    try {
      pipeline.fireTaskReceived();
      pipeline.fireTaskFiltered();
      pipeline.fireTaskExecuted();
    } finally {
      pipeline.fireAfterCompletion();
    }
  }
  
  private Pipeline newPipeline(Request request) {
    return context.getBean(DefaultPipeline.class, request);
  }
}
```

​    这里我们模拟了一个客户端的调用，首先创建了一个`Pipeline`对象，然后依次调用其各个层级的方法，并且这里我们使用try…finally结构来保证`Pipeline.fireAfterCompletion()`方法一定会执行。如此我们就完成了整个责任链模式的构造。这里我们使用前面用到的时效性过滤的filter来作为示例来实现一个`Handler`：

```java
@Component
public class DurationHandler implements Handler {

  @Override
  public void filterTask(HandlerContext ctx, Task task) {
    System.out.println("时效性检验");
    ctx.fireTaskFiltered(task);
  }
}
```

​    关于这里的具体业务`Handler`我们需要说明的有如下几点：

- 该`Handler`必须使用`@Conponent`注解来将其声明为Spring容器所管理的一个bean，这样我们前面实现的`HandlerBeanProcessor`才能将其动态的添加到整个`Pipeline`中；
- 在每个`Handler`中，需要根据当前的业务需要来实现具体的层级方法，比如这里是进行时效性检验，就是"任务过滤"这一层级的逻辑，因为时效性检验通过我们才能执行这个task，因而这里需要实现的是`Handler.filterTask()`方法，如果我们需要实现的是执行task的逻辑，那么需要实现的就是`Handler.executeTask()`方法；
- 在实现完具体的业务逻辑之后，我们可以根据当前的业务需要看是否需要将当前层级的链继续往下传递，也就是这里的`ctx.fireTaskFiltered(task);`方法的调用，我们可以看前面`HandlerContext.fireXXX()`方法就是会获取当前节点的下一个节点，然后进行调用。如果根据业务需要，不需要将链往下传递，那么就不需要调用`ctx.fireTaskFiltered(task);`；

### 3. 小结

​    如此，我们就通过两种方式实现了责任链模式，而且我们实现的责任链模式都是符合"开-闭"原则的，也就是说后续我们要为链添加新的节点的时候，只需要根据规范实现相应的接口即可，而无需处理链的维护相关的工作。关于第二种实现方式，这里我们并没有实现链节点的顺序控制功能，以及如何动态的添加或删除链的节点，更有甚者，如果控制每个Handler是单例的还是多例的。当然，有了前面的框架，这些点实现起来也比较简单，这里权当起到一个抛砖引玉的作用，读者朋友可根据自己的需要进行实现。