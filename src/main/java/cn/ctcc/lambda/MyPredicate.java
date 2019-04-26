package cn.ctcc.lambda;

/**
 * @Author: zk
 * @Date: 2019/4/3 17:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@FunctionalInterface
public interface MyPredicate<T> {


    boolean test(T t);
}
