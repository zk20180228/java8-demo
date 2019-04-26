package cn.ctcc.lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.*;
import java.util.function.*;

/**
 * @Author: zk
 * @Date: 2019/4/3 16:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class LambadTest {


    /**
     * 原来的内部类
     */
    @Test
    public void test01(){

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                return o1.compareTo(o2);
            }
        };

        TreeSet<String> set = new TreeSet<>(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });

        set.add("dsad");
        set.add("sa");

        System.out.println(set);
    }

    /**
     * Lambad方式实现
     */
    @Test
    public void test02(){

        //仔细观察发现Comparator方法不止一个抽象方法，但是也是函数式接口，
        //这是因为，函数式接口可以声明Object中提供的修饰符为public的方法,否者就不是函数式接口,
        //因为接口中的方法默认是public，子类的修饰符权限要大于等于父类的修饰符权限
        TreeSet<String> set = new TreeSet<>((str1, str2) -> str1.compareTo(str2));
        set.add("c");
        set.add("cd");
        set.add("a");
        System.out.println(set);
    }

/*************************************************************************************************************************************************/
    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );


    /**
     * 需求：获取公司中年龄小于 35 的员工信息
     */
    @Test
    public void test03(){

        List<Employee> employees = filterEmployeeByAge(emps);
        System.out.println(employees);


    }

    public List<Employee> filterEmployeeByAge(List<Employee> list){

        List<Employee> employees = new ArrayList<>();
        for(Employee e:list){
            if(e.getAge()<35){
                employees.add(e);
            }
        }

        return employees;
    }
    /**
     *     需求：获取公司中工资大于 5000 的员工信息
     */
    public List<Employee> filterEmployeeBySalary(List<Employee> emps){
        List<Employee> list = new ArrayList<>();

        for (Employee emp : emps) {
            if(emp.getSalary() > 5000){
                list.add(emp);
            }
        }

        return list;
    }


    /**
     * 优化方式一：策略设计模式
     *
     */

    public List<Employee> filterEmployee(List<Employee> emps, MyPredicate<Employee> mp){

        List<Employee> employees = new ArrayList<>();
        for(Employee e:emps){
            if(mp.test(e)){
                employees.add(e);
            }
        }

        return employees;
    }


    @Test
    public void test04(){

        List<Employee> employees = filterEmployee(emps, new AgePredicate());
        System.out.println(employees);

        System.out.println("--------------------------------------");

        List<Employee> list = filterEmployee(emps, new SalaryPredicate());
        System.out.println(list);

    }

    /**
     * 优化方式三：匿名内部类
     */
    @Test
    public void test05(){

        List<Employee> list1 = filterEmployee(emps, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() < 35;
            }
        });

        System.out.println(list1);
        System.out.println("--------------------------------------------------------");

        List<Employee> list2 = filterEmployee(emps, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() < 5000;
            }
        });

        System.out.println(list2);
    }

    /**
     * 优化方式三：Lambad表达式
     *
     */
    @Test
    public void test06(){

        //类型推断
        List<Employee> list1 = filterEmployee(emps, e -> e.getAge() < 35);
        System.out.println(list1);
        System.out.println("--------------------------------------------");

        List<Employee> list2 = filterEmployee(emps, e -> e.getSalary() < 5000);
        System.out.println(list2);
    }


    /**
     * 优化方式4 StreamAPI
     */
    @Test
    public void test07(){

        emps.stream()
            .filter(e->e.getAge()<35)
            .forEach(System.out::print);
        System.out.println("");
        System.out.println("------------------------------------------------");

        emps.stream()
            .filter(e->e.getSalary()>1000)
            .sorted((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()))
            .limit(3)
            .forEach(System.out::print);
    }

    /*********************************************************语法部分*******************************************************************/

    /**
     * Lambad表达式的基础语法：java8中引入了一个新的操作符“->”，该操作符被称为箭头操作符或Lambda操作符
     * 箭头操作符将Lambda表达式拆分为2部分
     * 左侧：Lambda表达式的形参列表
     * 右侧：Lambda体
     *
     * 语法格式：
     * 无参数，无返回值
     * ()->System.out.println("Hello Java8 !")
     * 有一个参数，无返回值
     * (x)->System.out.println(x)
     * x->System.out.println(x)
     * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
     *		Comparator<Integer> com = (x, y) -> {
     *			System.out.println("函数式接口");
     *			return Integer.compare(x, y);
     *		};
     *
     * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
     * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
     *
     * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
     * 		(Integer x, Integer y) -> Integer.compare(x, y);
     */
    @Test
    public void test08(){

        //jdk1.7之前，内部类访问外部类的变量,变量必须声明为final类型的,现在可以不声明为final类型的，但是不能在内部类里面更改变量的值
        int num =0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("num:"+num);
            }
        };


        Runnable r=System.out::println;

        r.run();
    }


    @Test
    public void test10(){

        Consumer<String> consumer=System.out::println;

        consumer.accept("你好java8");
    }


    @Test
    public void test11(){

        Comparator<Integer> comparator=Integer::compare;

        Integer x=10;
        Integer y=15;

        int rs = comparator.compare(x, y);
        if(rs==0){
            System.out.println("x=y");
        }
        if(rs<0){
            System.out.println("x<y");
        }
        if(rs>0){
            System.out.println("x>y");
        }
    }

    /**
     * 需求对一个数进行运算
     */
    @Test
    public void test12(){

        int sourceNum=12;
        Integer num1 = this.optionNum(sourceNum, e->  e * e);
        System.out.println(num1);

        Integer num2 = this.optionNum(sourceNum, e -> e * 100);
        System.out.println(num2);


    }


    public Integer optionNum(Integer sourceNum, MyFunction mf){

        return mf.getValue(sourceNum);
    }

/******************************************分隔符****************************************************************/

/**
 * Java8 内置的四大核心函数式接口
 *  Consumer<T>:消费型接口
 *      void accept(T t);
 *
 * Supplier<T>:供给型接口
 *      T get();
 *
 *  Function<T,R>:函数式接口
 *      R apply(T t);
 *
 *  Predicate<T>:断言型接口
 *      boolean test(T t);
 *
 *
 */
    /**
     * Predicate 接口
     */
    @Test
    public void test13(){

        String [] strs=new String[]{"aa","bb","ddd","hello","word"};

        List<String> list = new ArrayList<>();
        Predicate<String> p=e->e.length()>3;
        for(String str:strs){
            if(p.test(str)){
                list.add(str);
            }
        }

        list.stream().forEach(System.out::println);
    }


    /**
     * Function<T,R>接口
     */
    @Test
    public void test14(){

        String str="aa";

        String s1 = strHandler(str, e -> e + "=====");
        System.out.println(s1);
    }


    public String strHandler(String str, Function<String,String> ft){

        return ft.apply(str);
    }

    /**
     * Supplier<T> 供给型接口
     */
    @Test
    public void test15(){

        List<Integer> nums = getNums(10, () -> (int) (Math.random() * 100));
        nums.forEach(num-> System.out.print(num+","));

    }


    /**
     * 产生指定个数的随机数
     * @return
     */
    public List<Integer>  getNums(int nums, Supplier<Integer> sp){

        List<Integer> list = new ArrayList<>();
        for(int i=0;i<nums;i++){
            list.add(sp.get());
        }
        return list;
    }

    /**
     * Consumer<T>:消费型接口
     */
    @Test
    public void test16(){

        String[] strs=new String[]{"aa","bb","cc"};

        Arrays.stream(strs).forEach(t-> System.out.println(t));

    }

/******************************************************分隔符**********************************************************/


/**
 * 一:方法引用 :若Lambda表达式中的功能，已经有方法提供了实现，可以使用方法引用
 * 方法引用的三种形式：
 * 对象::实例方法名
 * 类名::静态方法名
 * 类名::实例方法
 *
 * 注意:
 * 1)所引用的方法的参数列表要和Lambda对应的函数式接口的抽象方法的参数列表和返回值一致
 * 2)若Lambda参数列表的第一个参数是实例方法的调用者，第二个参数（或者无参）是实例方法的参数时，可以使用：ClassName::MethodName
 *
 * 二:构造器引用->构造器的参数列表要和函数是接口中的参数列表一致 ClassName::new
 *
 * 三:数组引用->类型[]::new
 *
 */


    @Test
    public void test17(){

        PrintStream out = System.out;

        //内部类访问局部变量
        Consumer<String> con01=e->out.println(e);
        con01.accept("hello word!");

        //内部类访问局部变量
        Consumer<String> con02=out::println;
        con02.accept("hello java8");

        //方法引用：引用的方法的参数列表和返回值个函数式接口抽象方法一致
        Consumer<String> con3=System.out::println;
        con3.accept("方法引用的参数列表和返回值要和函数式接口的抽象方法一致！");
    }


    /**
     * 方法引用：实例对象：方法名
     */
    @Test
    public void test18(){

        Employee emp = new Employee(1, "张三", 23, 8888.88);
        //内部类访问局部变量
        Supplier<String> empName=()->emp.getName();
        System.out.println(empName.get());
        /**************************************************************************/

        Supplier<Double> salary=emp::getSalary;
        System.out.println(salary.get());
    }

    /**
     * 类名::静态方法名
     */
    @Test
    public void test19(){

        BiFunction<Double,Double,Double> max=(x,y)->Math.max(x,y);
        System.out.println(max.apply(333.33,555.55));

        BiFunction<Integer,Integer,Integer> maxInt=Math::max;
        System.out.println(maxInt.apply(3,8));

        Comparator<Integer> comparator1=(x,y)->Integer.compare(x,y);

        Comparator<Integer> comparator2=Integer::compare;
        int rs = comparator2.compare(3, 3);
        if(rs==0){
            System.out.println("相等");
        }else if(rs<0){
            System.out.println("小于");
        }else {
            System.out.println("大于");
        }
    }


    /**
     * 类名::实例方法名
     */
    @Test
    public void test20(){

        BiPredicate<String, String> bp=(x, y)->x.equals(y);
        boolean flag1 = bp.test("a", "a");
        System.out.println(flag1);

        BiPredicate<String,String> b=String::equals;
        boolean flag2=b.test("a","b");
        System.out.println(flag2);

        Employee emp = new Employee("张三",23);

        Function<Employee,String> f=e->e.toString();
        String str = f.apply(emp);
        System.out.println(str);

        //抽象方法的第一个参数是实例方法的调用者
        Function<Employee,String> ff=Employee::toString;
        String s = ff.apply(emp);
        System.out.println(s);
    }


    /**
     * 类名::new:构造器引用
     */
    @Test
    public void test21(){

        Supplier<Employee> sp=()->new Employee();
        Employee emp = sp.get();
        System.out.println(emp);

        Supplier<Employee> p=Employee::new;
        System.out.println(p.get());
    }


    /**
     * 数组引用 类型[]::new
     */
    @Test
    public void test22(){

        Function<Integer,String[]> f=i->new String[i];
        String[] strs = f.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> ff=String[]::new;
        String[] apply = ff.apply(20);
        System.out.println(apply.length);

        Function<Integer,Employee[]> fff=Employee[]::new;
        Employee[] emps = fff.apply(30);
        System.out.println(emps.length);
    }


    /********************************************************分隔符*********************************************/
   @Test
   public void test23(){

       Collections.sort(emps,(e1,e2)->{
           if(e1.getAge()==e2.getAge()){
               return e1.getName().compareTo(e2.getName());
           }else{
               return -Integer.compare(e1.getAge(),e2.getAge());
           }
       });

       emps.forEach(e-> System.out.println(e));
   }


   @Test
   public void test24(){

       Function<String,String> f1=String::trim;
       String helloWord = f1.apply("    helloWord !   ");
       System.out.println(helloWord);

       Function<String,Integer> f2=String::length;
       Integer length = f2.apply("你好！");
       System.out.println(length);

       BiFunction<Integer,Integer,String> bf="hahahh"::substring;
       String sub = bf.apply(0, 2);
       System.out.println(sub);
   }


   @Test
   public void test25(){

       BinaryOperator<Integer> bo=(x,y)->x*y;
       Integer s = bo.apply(2, 3);
       System.out.println(s);

   }

}
