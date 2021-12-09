package com.atguigu.demo.controller;

import com.atguigu.demo.bean.People;
import com.atguigu.demo.service.PeopleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *    @author  : shkstart
 *    email   : shkstart@126.com
 *    time    : 08:54
 *    desc    : 内存测试
 *    version : v1.0
 * </pre>
 */
@RestController
public class MemoryTestController {
    @Autowired
    private PeopleSevice peopleSevice;

    /**
     * 案例1：模拟线上环境OOM
     */
    @RequestMapping("/add")
    public void addObject(){
        System.err.println("add"+peopleSevice);
        ArrayList<People> people = new ArrayList<>();
        while (true){
            people.add(new People());
        }
    }


    /**
     * 案例2:模拟元空间OOM溢出
     */
    @RequestMapping("/metaSpaceOom")
    public void metaSpaceOom(){
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(People.class);
//            enhancer.setUseCache(false);
            enhancer.setUseCache(true);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
                System.out.println("我是加强类，输出print之前的加强方法");
                return methodProxy.invokeSuper(o,objects);
            });
            People people = (People)enhancer.create();
            people.print();
            System.out.println(people.getClass());
            System.out.println("totalClass:" + classLoadingMXBean.getTotalLoadedClassCount());
            System.out.println("activeClass:" + classLoadingMXBean.getLoadedClassCount());
            System.out.println("unloadedClass:" + classLoadingMXBean.getUnloadedClassCount());
        }
    }

    /**
     * 性能优化案例3：合理配置堆内存
     */
    @RequestMapping("/getData")
    public List<People> getProduct(){
        List<People> peopleList = peopleSevice.getPeopleList();
        return peopleList;
    }
}
