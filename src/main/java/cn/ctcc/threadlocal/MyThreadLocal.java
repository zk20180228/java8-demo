package cn.ctcc.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2019/5/16 14:07
 * @Description:
 *  自定义一个简单的ThreadLocal,该TreadLocal和java.lang包下的ThreadLocal有着很大的不同。
 *  让我先来屡屡，java.lang.ThreadLocal
 *   1)该对象内部有一个内部类map,叫做ThreadLocalMap
 *   2)Thread线程类内部维护一个ThreadLocalMap成员
 *   3)很多地方（get(),set(T t)）用到了Thread.currentThread()获取当前线程
 *   4）获取当前线程ThreadLocalMap
 *   5)ThreadLocalMap.put(this,t),注意最大的不同点出现了，此处的this,是指ThreadLocal本身
 *   6)get()--->获取当前线程对象Thread.currentThread()-->获取ThreadLocalMap,获取存入夫人值
 *   7)set(T t)-->获取当前线程Thread.currentThread()-->取ThreadLocalMap,如果为null则创建ThreadLocalMap，然后设置数据
 *
 *   总上所述,ThreadLocal的原理是：当前线程对象获取自己的ThreadLocalMap,来获取值，多个线程的ThreadLocalMap的key是相同的，是TreadLocal本身
 *
 *  自定义的 MyThreadLocal思想和以上的不一样,如下：
 *  1)MyThreadLocal维护了一个final map
 *  2)get()-->map.get(Thread.currentThread())
 *  3)set(T t)-->map.get(Thread.currentThread(),t);
 *
 *  综上所述,MyThreadLocal的原理是，维护一个map，map的key是当前线程对象，来获取值的，map的key不相同，但是是同一个map
 *
 * @Modified:
 * @version: V1.0
 */
public class MyThreadLocal<T> {


    private Map<Thread, T> map = new HashMap<>();

    public void set(T t) {
        Thread thread = Thread.currentThread();
        map.put(thread, t);
    }


    public T get() {
        T t = map.get(Thread.currentThread());
        if (t == null) {
            t = init();
            map.put(Thread.currentThread(), t);
        }

        return t;
    }


    public T init() {

        return null;
    }

}
