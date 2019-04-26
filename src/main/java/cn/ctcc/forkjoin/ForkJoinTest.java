package cn.ctcc.forkjoin;

import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * @Author: zk
 * @Date: 2019/4/9 10:41
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class ForkJoinTest {


    @Test
    public void test01(){

        long start= Instant.now().toEpochMilli();
        ForkJoinCalculate task = new ForkJoinCalculate(0L, 10000000000L);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long sum = forkJoinPool.invoke(task);
        System.out.println(sum);
        long end=Instant.now().toEpochMilli();
        //2558
        System.out.println("耗费的时间为: "+(end-start));

    }


    @Test
    public void test2(){
        long start = Instant.now().toEpochMilli();
        long sum = 0L;
        for (long i = 0L; i <= 10000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);
        long end = Instant.now().toEpochMilli();
        //3621
        System.out.println("耗费的时间为: " + (end - start));
    }


    /**
     * DoubleStream ， IntStream ， LongStream
     */
    @Test
    public void test03(){

        long start=Instant.now().toEpochMilli();

        long sum = LongStream.rangeClosed(0L, 10000000000L)
                .parallel()
                .sum();
        System.out.println(sum);


        long end=Instant.now().toEpochMilli();
        //1964
        System.out.println("耗费的时间为: " + (end - start));
    }










}
