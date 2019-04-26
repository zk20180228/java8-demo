package cn.ctcc.stream;

import cn.ctcc.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zk
 * @Date: 2019/4/4 15:31
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * Stream API介绍
 * 1创建Stream·
 * 2中间操作
 * 3终止操作(终端操作)
 *
 */
public class StreamApiTest {

    /**
     * 创建Stream
     */
    @Test
    public void test01(){

        //1.Collection提供了两个方法。stream()和parallelStream()
        List<String> list = new ArrayList<>();
        //获取一个顺序流
        Stream<String> stream1 = list.stream();
        //获取一个并行流
        Stream<String> parallelStream = list.parallelStream();

        //2.通过Arrays的stream方法创建一个Stream
        String[] strs=new String[10];
        Stream<String> stream2 = Arrays.stream(strs);

        //3.通过Stream静态方法of创建一个Stream对象
        Stream<Integer> stream3 = Stream.of(1, 3, 5, 7, 9, 0);
        stream3.forEach(System.out::println);

        //4创建无限流

        //迭代
        Stream<Integer> iterateStream = Stream.iterate(1, x -> x + 1).limit(10);
        iterateStream.forEach(System.out::println);

        System.out.println("--------------------------------------------------------");

        //随机生成
        Stream<Double> doubleStream = Stream.generate(Math::random).limit(5);
        doubleStream.forEach(System.out::println);
    }



/***********************************************************分隔符******************************************************/

    //2. 中间操作
    List<Employee> emps = Arrays.asList(
        new Employee(102, "李四", 59, 6666.66),
        new Employee(101, "张三", 18, 9999.99),
        new Employee(103, "王五", 28, 3333.33),
        new Employee(104, "赵六", 8, 7777.77),
        new Employee(104, "赵六", 8, 7777.77),
        new Employee(104, "赵六", 8, 7777.77),
        new Employee(105, "田七", 38, 5555.55)
    );

    /**
     * 筛选与切片
     * filter->接受Lambda,从流中排除某些元素
     * limit->截断流，使其元素不超过指定的个数
     * skip->返回一个扔掉了前面n个元素的流，如果n大于元素个数，返回一个空流，常与limit()配合
     * distinct->筛选,根据元素的hashCode和equals去除重复元素
     *
     */
    @Test
    public void test02(){
        //中间操作不会做任何处理，惰性求值
        Stream<Employee> stream = emps.stream()
                .filter(e -> e.getAge() <= 35);

        //只有做终止操作时，所有操作会一次性执行
        //内部迭代
        stream.forEach(System.out::println);

        //外部迭代
        Iterator<Employee> iterator = emps.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+",");
        }
    }

    @Test
    public void test03(){

        emps.stream()
            .filter(e->e.getSalary()>=5000)
            .sorted((e1,e2)->-Double.compare(e1.getSalary(),e2.getSalary()))
            .limit(2)
            .forEach(System.out::println);
    }

    @Test
    public void test04(){

        emps.stream()
            .filter(e->e.getSalary()>=5000)
            .skip(2)
            .forEach(e-> System.out.println(e));
    }


    @Test
    public void test05(){

        emps.stream()
            .filter(e->e.getSalary()>=5000)
            .distinct()
            .forEach(e-> System.out.println(e));
    }

/***********************************************************************分隔符********************************************************/

    /**
     * 映射
     * map->接受Lambda,将元素转换为其他形式或提取信息。接受一个函数会作为参数，还函数会被应用到每个元素上，并将其映射为一个新的元素
     * flatMap->接受一个函数作为参数，将流中的值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test06(){

        emps.stream()
            .map(e->e.getName())
            .sorted()
            .distinct()
            .forEach(System.out::println);

        List<String> strList = Arrays.asList("aa", "bb", "cc", "dd", "ee");
        Stream<String> strListStream = strList.stream()
                .map(String::toUpperCase);
        strListStream.forEach(System.out::print);
        System.out.println("");
        System.out.println("---------------------------------------------------------------------");
        Stream<Stream<Character>> characterStream = strList.stream()
                                                            .map(e -> filterCharacter(e));
        characterStream.forEach(e->e.forEach(System.out::print));

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Stream<Character> characterStream2 = strList.stream()
                .flatMap(e -> filterCharacter(e));
        //java.lang.IllegalStateException: stream has already been operated upon or closed 已经终止 的stream无法再次使用
        characterStream2.forEach(System.out::print);
    }

    public Stream<Character> filterCharacter(String str){

        List<Character> list = new ArrayList<>();
        for(Character c:str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    /**
     *  sort()->自然排序
     *  sort(Comparator<T> com)->定制排序
     */
    @Test
    public void test07(){

        emps.stream()
            .map(e->e.getName())
            .distinct()
            .sorted()
            .forEach(System.out::print);
        System.out.println("");
        emps.stream()
            .map(e->e.getName())
            .distinct()
            .sorted((e1,e2)->-e1.compareTo(e2))
            .forEach(System.out::print);
    }

/*************************************************************分隔符***************************************************/

    /**
     * 终止操作
     * allMatch->检查是否匹配所有元素
     * anyMatch->检查是否至少匹配一个元素
     * noneMatch->检查是否没有匹配的元素
     * findFirst->返回第一个元素
     * findAny->返回当前流中任意元素
     * count->返回流中元素的总个数
     * max->返回流中最大值
     * min->返回流中最小值
     */
    @Test
    public void test08(){

        List<Employee> list = Arrays.asList(
                new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
                new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
                new Employee(103, "王五", 28, 3333.33,Employee.Status.VOCATION),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.FREE),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.BUSY),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.BUSY),
                new Employee(105, "田七", 38, 5555.55,Employee.Status.FREE)
        );

        boolean flag1 = list.stream()
                .anyMatch(e -> e.getStatus() == Employee.Status.BUSY);
        System.out.println(flag1);

        boolean flag2 = list.stream()
                .allMatch(e -> Employee.Status.BUSY.equals(e.getStatus()));
        System.out.println(flag2);

        boolean flag3 = list.stream()
                .noneMatch(e -> Employee.Status.VOCATION.equals(e.getStatus()));
        System.out.println(flag3);

        Optional<Employee> firstEmpBySalary = list.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();

        Employee emp1 = firstEmpBySalary.get();
        System.out.println(emp1);

        Optional<Employee> any = list.parallelStream()
                .filter(e1 -> Employee.Status.BUSY.equals(e1.getStatus()))
                .findAny();

        Employee anyEmp = any.get();
        System.out.println(anyEmp);

        long count = list.stream()
                         .filter(e -> Employee.Status.FREE.equals(e.getStatus()))
                         .count();
        System.out.println(count);

        Optional<Double> max = list.stream()
                .map(Employee::getSalary)
                .max(Double::compareTo);
        Double maxDouble = max.get();
        System.out.println(maxDouble);

        Optional<Employee> min = list.stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
        Employee minEmp = min.get();
        System.out.println(minEmp);


    }

/********************************************************分隔符*********************************************************/


    /**
     * 归约
     * reduce(T identity,BinaryOperator bo)
     * reduce(BinaryOperator bo)
     * 可以将流中的元素反复结合起来，得到一个值
     *
     */
    @Test
    public void test09(){

        List<Integer> list = Arrays.asList(1, 3, 5, 7);
        Optional<Integer> num1 = list.stream()
                .reduce((x, y) -> x + y);
        System.out.println(num1.get());


        List<Employee> list02 = Arrays.asList(
                new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
                new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
                new Employee(103, "王五", 28, 3333.33,Employee.Status.VOCATION),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.FREE),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.BUSY),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.BUSY),
                new Employee(105, "田七", 38, 5555.55,Employee.Status.FREE)
        );

        Optional<Double> sum01 = list02.stream()
                .map(e -> e.getSalary())
                .reduce((x, y) -> x + y);
        System.out.println(sum01.get());

        Optional<Double> sum02 = list02.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(sum01.get().equals(sum02.get()));
    }

    /**
     * 需求:统计名字中"六"出现的次数
     */
    @Test
    public void test10(){

        Optional<Integer> count = emps.stream()
                .map(e -> {
                    if (e.getName().contains("六")) {
                        return 1;
                    } else {
                        return 0;
                    }
                }).reduce(Integer::sum);
        System.out.println(count.get());
    }


    /**
     * collect:收集,将流转换为其他形式。接受一个Collector接口的实现，用于给Stream中元素做汇总
     * Collector的实现常常用Collectors来获取
     */
    @Test
    public void test11(){

        List<String> list01 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        list01.stream()
              .forEach(System.out::print);

        System.out.println("");
        System.out.println("------------------------------------------------------------");

        Set<String> set01 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set01.stream()
             .forEach(System.out::print);

        System.out.println("");
        System.out.println("------------------------------------------------------------");

        HashSet<String> hashSet01 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet01.forEach(e-> System.out.print(e));

        System.out.println("");
        //找出最大值
        Optional<Double> d1 = emps.stream()
                .map(e -> e.getSalary())
                .collect(Collectors.maxBy(Double::compare));
        System.out.println("max="+d1.get());

        //找出最小值
        Optional<Double> d2 = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println("min="+d2.get());

        //求和
        Double d3 = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.summingDouble(e -> e));
        System.out.println("sum="+d3);

        //求平均值
        Double d4 = emps.stream()
                .collect(Collectors.averagingDouble(e -> e.getSalary()));
        System.out.println("avg="+d4);

        //统计数量
        Long d5 = emps.stream()
                .collect(Collectors.counting());
        System.out.println(d5);

        //摘要封装->求最大值
        DoubleSummaryStatistics d6 = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(d6.getMax());
        System.out.println(d6.getMin());


        List<Employee> mps = Arrays.asList(
                new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
                new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
                new Employee(103, "王五", 28, 3333.33,Employee.Status.VOCATION),
                new Employee(104, "赵六", 8, 7777.77,Employee.Status.FREE),
                new Employee(105, "田七", 38, 5555.55,Employee.Status.FREE)
        );


        //分组
        Map<Employee.Status, List<Employee>> d7 = mps.stream()
                .collect(Collectors.groupingBy(e -> e.getStatus()));
        d7.forEach((e,v)->{
            System.out.println(e.name());
            v.forEach(y-> System.out.println(y.getName()));
        });

        //多级分组
        Map<Employee.Status, Map<String, List<Employee>>> d8 = mps.stream()
                .collect(Collectors.groupingBy(e -> e.getStatus(), Collectors.groupingBy(e -> {
                    if (e.getAge() >= 60) {
                        return "老年";
                    } else if (e.getAge() >= 35) {
                        return "中年";
                    } else {
                        return "青年";
                    }
                })));
        System.out.println(d8);

        //分区(根据boolean条件分区，分为满足和不满足)
        Map<Boolean, List<Employee>> d9 = mps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 4500));
        System.out.println(d9);


        //字符连接
        String d10 = mps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "", ""));
        System.out.println(d10);

        //归约
        Optional<Double> d11 = mps.stream()
                .map(e -> e.getSalary())
                .collect(Collectors.reducing((e1, e2) -> e1 + e2));
        System.out.println(d11.get());


        //映射
        Map<String, Double> map = mps.stream()
                .collect(Collectors.toMap(Employee::getName, Employee::getSalary));
        map.forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        //返回线程安全的map
        ConcurrentMap<String, Double> concurrentMap = mps.stream()
                .collect(Collectors.toConcurrentMap(Employee::getName, Employee::getSalary));

        concurrentMap.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }


}
