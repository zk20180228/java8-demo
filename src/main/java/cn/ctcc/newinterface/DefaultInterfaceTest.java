package cn.ctcc.newinterface;

/**
 * @Author: zk
 * @Date: 2019/4/8 14:33
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class DefaultInterfaceTest {

    public static void main(String[] args) {

        SubClass subClass = new SubClass();
        //1.当继承的类中含有与实现的接口一样的default方法，以继承类为准
        //2.当继承类中无与实现的接口一样的default方法时，且多个接口有着相同的default方法，此时实现类必须重写该方法，指明该方法由哪个接口实现或自己实现
        System.out.println(subClass.getName());
        System.out.println(subClass.getAge());

        //接口中的静态方法，只为接口使用，实现类无法使用
        MyInterface.show();
    }
}
