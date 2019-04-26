package cn.ctcc.optional;

import cn.ctcc.lambda.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * @Author: zk
 * @Date: 2019/4/8 15:01
 * @Description:
 * Optional容器类:用于尽量避免空指针异常
 * Optional.of(T t):用于创建一个Optional实例,当t为null，编译器会报错
 * Optional.empty():用于创建一个空的Optional实力
 * Optional.ofNullable(T t):创建一个Optional实例，当t为空时,创建一个空的Optional实例
 * isPresent():判断是否包含值
 * orElse(T t):如果包含值，则返回该值，否则返回t
 * orElseGet(Supplier s):如果调用对象包含值，则返回该值，否则返回s获取的值
 * map(Function f):如果有值，对其进行处理，返回处理后的Optional，否则返回Optional.empty()
 * flatMap(Function mapper):与map类似，要求返回值必须是Optional
 * @Modified:
 * @version: V1.0
 */
public class OptionalTest {


    @Test
    public void test01(){

        Optional<Employee> empOptional = Optional.ofNullable(new Employee());
        if(empOptional.isPresent()){
            //Employee{id=0, name='null', age=0, salary=0.0, status=null}
            System.out.println(empOptional.get());
        }

        Employee e1 = empOptional.orElse(new Employee("张三"));
        //null
        System.out.println(e1.getName());

        Employee ep = empOptional.orElseGet(Employee::new);
        //Employee{id=0, name='null', age=0, salary=0.0, status=null}
        System.out.println(ep);

    }


    @Test
    public void test02(){

        Optional<Employee> op = Optional.of(new Employee("张三"));
        Optional<String> s = op.map(e -> e.getName());
        if(s.isPresent()){
            //张三
            System.out.println(s.get());
        }

        Optional<String> ss = op.flatMap(e -> Optional.of(e.getName()));
        if(ss.isPresent()){
            //张三
            System.out.println(ss.get());
        }

    }



}
