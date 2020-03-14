### 接口调用方式

- 原生ajax
- 基于jQuery的ajax
- fetch
- axios

###  异步

- JavaScript的执行环境是「单线程」
- 所谓单线程，是指JS引擎中负责解释和执行JavaScript代码的线程只有一个，也就是一次只能完成一项任务，这个任务执行完后才能执行下一个，它会「阻塞」其他任务。这个任务可称为主线程
- 异步模式可以一起执行**多个任务**
- JS中常见的异步调用
  - 定时任何
  - ajax
  - 事件函数

### promise

- 主要解决异步深层嵌套的问题
- promise 提供了简洁的API  使得异步操作更加容易

```html
<script type="text/javascript">
/*
 1. Promise基本使用
       我们使用new来构建一个Promise  Promise的构造函数接收一个参数，是函数，
       并且传入两个参数：resolve，reject， 分别表示异步操作执行成功后的回调函数
       和异步操作执行失败后的回调函数
*/

var p = new Promise(function(resolve, reject){
  //2. 这里用于实现异步任务  setTimeout
  setTimeout(function(){
    var flag = false;
    if(flag) {
      //3. 正常情况
      resolve('hello');
    }else{
      //4. 异常情况
      reject('出错了');
    }
  }, 100);
});
//  5 Promise实例生成以后，可以用then方法指定resolved状态和reject状态的回调函数 
//  在then方法中，你也可以直接return数据而不是Promise对象，在后面的then中就可以接收到数据了  
p.then(function(data){
  console.log(data)
},function(info){
  console.log(info)
});
</script>
```

###  基于Promise发送Ajax请求

```html
 
<script type="text/javascript">
/*
  基于Promise发送Ajax请求
*/
function queryData(url) {
  var p = new Promise(function(resolve, reject){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
      if(xhr.readyState != 4) return;
      if(xhr.readyState == 4 && xhr.status == 200) {
        resolve(xhr.responseText);
      }else{
        reject('服务器错误');
      }
    };
    xhr.open('get', url);
    xhr.send(null);
  });
  return p;
}
// 注意：  这里需要开启一个服务 
// 在then方法中，你也可以直接return数据而不是Promise对象，在后面的then中就可以接收到数据了
queryData('http://localhost:3000/data')
  .then(function(data){
    console.log(data)
    //想要继续链式编程下去 需要 return  
    return queryData('http://localhost:3000/data1');
  })
  .then(function(data){
    console.log(data);
    return queryData('http://localhost:3000/data2');
  })
  .then(function(data){
    console.log(data)
  });
</script>
```

### Promise  基本API

####  实例方法

##### .then()

- 得到异步任务正确的结果

##### .catch()

- 获取异常信息

##### .finally()

- 成功与否都会执行（不是正式标准） 

```html
  
<script type="text/javascript">
/*
  Promise常用API-实例方法
*/
// console.dir(Promise);
function foo() {
  return new Promise(function(resolve, reject){
    setTimeout(function(){
      // resolve(123);
      reject('error');
    }, 100);
  })
}
// foo()
//   .then(function(data){
//     console.log(data)
//   })
//   .catch(function(data){
//     console.log(data)
//   })
//   .finally(function(){
//     console.log('finished')
//   });

// --------------------------
// 两种写法是等效的
foo()
  .then(function(data){
    // 得到异步任务正确的结果
    console.log(data)
  },function(data){
    // 获取异常信息
    console.log(data)
  })
  // 成功与否都会执行（不是正式标准） 
  .finally(function(){
    console.log('finished')
  });
</script>
```

#### 静态方法

#####  .all()
- `Promise.all`方法接受一个数组作参数，数组中的对象（p1、p2、p3）均为promise实例（如果不是一个promise，该项会被用`Promise.resolve`转换为一个promise)。它的状态由这三个promise实例决定

#####  .race()
- `Promise.race`方法同样接受一个数组作参数。当p1, p2, p3中有一个实例的状态发生改变（变为`fulfilled`或`rejected`），p的状态就跟着改变。并把第一个改变状态的promise的返回值，传给p的回调函数

```html
<script type="text/javascript">
/*
  Promise常用API-对象方法
*/
// console.dir(Promise)
function queryData(url) {
  return new Promise(function(resolve, reject){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
      if(xhr.readyState != 4) return;
      if(xhr.readyState == 4 && xhr.status == 200) {
        // 处理正常的情况
        resolve(xhr.responseText);
      }else{
        // 处理异常情况
        reject('服务器错误');
      }
    };
    xhr.open('get', url);
    xhr.send(null);
  });
}

var p1 = queryData('http://localhost:3000/a1');
var p2 = queryData('http://localhost:3000/a2');
var p3 = queryData('http://localhost:3000/a3');
 Promise.all([p1,p2,p3]).then(function(result){
   //   all 中的参数  [p1,p2,p3]   和 返回的结果一 一对应["HELLO TOM", "HELLO JERRY", "HELLO SPIKE"]
   console.log(result) //["HELLO TOM", "HELLO JERRY", "HELLO SPIKE"]
 })
Promise.race([p1,p2,p3]).then(function(result){
  // 由于p1执行较快，Promise的then()将获得结果'P1'。p2,p3仍在继续执行，但执行结果将被丢弃。
  console.log(result) // "HELLO TOM"
})
</script>
```

###  fetch

- Fetch API是新的ajax解决方案 Fetch会返回Promise
- **fetch不是ajax的进一步封装，而是原生js，没有使用XMLHttpRequest对象**。
- fetch(url, options).then(）

```html
  <script type="text/javascript">
    /*
      Fetch API 基本用法
      	fetch(url).then()
     	第一个参数请求的路径   Fetch会返回Promise   所以我们可以使用then 拿到请求成功的结果 
    */
    fetch('http://localhost:3000/fdata').then(function(data){
      // text()方法属于fetchAPI的一部分，它返回一个Promise实例对象，用于获取后台返回的数据
      return data.text();
    }).then(function(data){
      //   在这个then里面我们能拿到最终的数据  
      console.log(data);
    })
  </script>
```

####  fetch API中的HTTP请求

- fetch(url, options).then(）
- HTTP协议，它给我们提供了很多的方法，如POST，GET，DELETE，UPDATE，PATCH和PUT
  - 默认的是GET请求
  - 需要在options对象中指定对应的method, method:请求使用的方法 
  - post和普通请求的时候需要在options中设置请求头headers和body

```html
<script type="text/javascript">
    /*
          Fetch API 调用接口传递参数
    */
   //GET参数传递
    fetch('http://localhost:3000/books?id=123', {
            method: 'get'
        })
        .then(function(data) {
            // 它返回一个Promise实例对象，用于获取后台返回的数据
            return data.text();
        }).then(function(data) {
            // 在这个then里面我们能拿到最终的数据  
            console.log(data)
        });

  // GET参数传递 restful形式的URL  通过/ 的形式传递参数即id = 456 和id后台的配置有关   
    fetch('http://localhost:3000/books/456', {
            method: 'get'
        })
        .then(function(data) {
            return data.text();
        }).then(function(data) {
            console.log(data)
        });

   // DELETE请求方式参数传递,删除id是id=789
    fetch('http://localhost:3000/books/789', {
            method: 'delete'
        })
        .then(function(data) {
            return data.text();
        }).then(function(data) {
            console.log(data)
        });

   //POST请求传参
    fetch('http://localhost:3000/books', {
            method: 'post',
            body: 'uname=lisi&pwd=123',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
        .then(function(data) {
            return data.text();
        }).then(function(data) {
            console.log(data)
        });

    fetch('http://localhost:3000/books', {
            method: 'post',
            body: JSON.stringify({
                uname: '张三',
                pwd: '456'
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(function(data) {
            return data.text();
        }).then(function(data) {
            console.log(data)
        });

    fetch('http://localhost:3000/books/123', {
            method: 'put',
            body: JSON.stringify({
                uname: '张三',
                pwd: '789'
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(function(data) {
            return data.text();
        }).then(function(data) {
            console.log(data)
        });
</script>
```

####  fetchAPI中响应格式

- 用fetch来获取数据，如果响应正常返回，我们首先看到的是一个response对象，其中包括返回的一堆原始字节，这些字节需要在收到后，需要我们通过调用方法将其转换为相应格式的数据，比如`JSON`，`BLOB`或者`TEXT`等等

```js

/*
  Fetch响应结果的数据格式
*/
fetch('http://localhost:3000/json').then(function(data){
  // return data.json();   //  将获取到的数据使用 json 转换对象
  return data.text(); //  //  将获取到的数据 转换成字符串 
}).then(function(data){
  // console.log(data.uname)
  // console.log(typeof data)
  var obj = JSON.parse(data);
  console.log(obj.uname,obj.age,obj.gender)
})

```

###  axios

- 基于promise用于浏览器和node.js的http客户端
- 支持浏览器和node.js
- 支持promise
- 能拦截请求和响应
- 自动转换JSON数据
- 能转换请求和响应数据

#### axios基础用法

- get和 delete请求传递参数
  - 通过传统的url  以 ? 的形式传递参数
  -  restful 形式传递参数 
  - 通过params  形式传递参数 
- post  和 put  请求传递参数
  - 通过选项传递参数
  -  通过 URLSearchParams  传递参数 

```js
axios.get('http://localhost:3000/adata').then(function(ret){ 
  // 注意data属性是固定的用法，用于获取后台的实际数据
  // console.log(ret.data)
  console.log(ret)
})
axios.get('http://localhost:3000/axios?id=123').then(function(ret){
  console.log(ret.data)
})
axios.get('http://localhost:3000/axios/123').then(function(ret){
  console.log(ret.data)
})
axios.get('http://localhost:3000/axios', {
  params: {
    id: 789
  }
}).then(function(ret){
  console.log(ret.data)
})
axios.delete('http://localhost:3000/axios', {
  params: {
    id: 111
  }
}).then(function(ret){
  console.log(ret.data)
})

axios.post('http://localhost:3000/axios', {
  uname: 'lisi',
  pwd: 123
}).then(function(ret){
  console.log(ret.data)
})
var params = new URLSearchParams();
params.append('uname', 'zhangsan');
params.append('pwd', '111');
axios.post('http://localhost:3000/axios', params).then(function(ret){
  console.log(ret.data)
})

axios.put('http://localhost:3000/axios/123', {
  uname: 'lisi',
  pwd: 123
}).then(function(ret){
  console.log(ret.data)
})

```

#### axios全局配置

```js
// 配置公共的请求头 
axios.defaults.baseURL = 'https://api.example.com';
// 配置超时时间
axios.defaults.timeout = 2500;
// 配置公共的请求头
axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// 配置公共的post的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
```
####  axios拦截器

- 请求拦截器
  - 请求拦截器的作用是在请求发送前进行一些操作
    - 例如在每个请求体里加上token，统一做了处理如果以后要改也非常容易
- 响应拦截器
  - 响应拦截器的作用是在接收到响应后进行一些操作
    - 例如在服务器返回登录状态失效，需要重新登录的时候，跳转到登录页

```js
// 请求拦截器 
axios.interceptors.request.use(function(config) {
  console.log(config.url)
//  任何请求都会经过这一步   在发送请求之前做些什么   
  config.headers.mytoken = 'nihao';
// 这里一定要return   否则配置不成功  
  return config;
}, function(err){
   // 对请求错误做点什么    
  console.log(err)
})
// 响应拦截器 
axios.interceptors.response.use(function(res) {
// 在接收响应做些什么  
  var data = res.data;
  return data;
}, function(err){
// 对响应错误做点什么  
  console.log(err)
})
```

###  async和await

- async作为一个关键字放到函数前面
  - 任何一个`async`函数都会隐式返回一个`promise`
- `await`关键字只能在使用`async`定义的函数中使用
  - ​    await后面可以直接跟一个 Promise实例对象
  - ​     await函数不能单独使用
- **async/await 让异步代码看起来、表现起来更像同步代码**

```js
//  async 基础用法
// async作为一个关键字放到函数前面
async function queryData() {
  // await关键字只能在使用async定义的函数中使用, await后面可以直接跟一个 Promise实例对象
  var ret = await new Promise(function(resolve, reject){
    setTimeout(function(){
      resolve('nihao')
    },1000);
  })
  // console.log(ret.data)
  return ret;
}
// 任何一个async函数都会隐式返回一个promise,我们可以使用then进行链式编程
queryData().then(function(data){
  console.log(data)
})

// async函数处理多个异步函数
axios.defaults.baseURL = 'http://localhost:3000';

async function queryData() {
  // 添加await之后,当前的await 返回结果之后才会执行后面的代码   
  
  var info = await axios.get('async1');
  // 让异步代码表现起来更像同步代码
  var ret = await axios.get('async2?info=' + info.data);
  return ret.data;
}

queryData().then(function(data){
  console.log(data)
})
```