package cn.ctcc.newdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zk
 * @Date: 2019/4/9 9:59
 * @Description: 使用线程局部变量来解决SimpleDateFormat的多线程安全问题
 *
 * @Modified:
 * @version: V1.0
 */
public class SimpleDateFormatTest02 {


    public static void main(String[] args) {

        String str="2019-04-09 10:09:30";
        ExecutorService pool = Executors.newFixedThreadPool(10);

        Callable<Date> task=()->DateFormatThreadLocal.parse(str);

        List<Future<Date>> list = new ArrayList<>();
        for(int i=0;i<50;i++){
            list.add(pool.submit(task));
        }

        pool.shutdown();

        list.forEach(e-> {
            try {
                System.out.println(e.get());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


    }


}
