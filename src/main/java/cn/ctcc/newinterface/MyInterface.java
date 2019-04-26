package cn.ctcc.newinterface;

/**
 * @Author: zk
 * @Date: 2019/4/8 14:46
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public interface MyInterface {

    default String getName(){
        return "呵呵";
    }

    default int getAge(){
        return 18;
    }

    static void show(){
        System.out.println("接口中的static方法");
    }

}
