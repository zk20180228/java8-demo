package cn.ctcc.newinterface;

/**
 * @Author: zk
 * @Date: 2019/4/8 14:51
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class SubClass /*extends MyClass */implements MyFun,MyInterface {


    @Override
    public String getName() {
        return MyFun.super.getName();
    }
}
