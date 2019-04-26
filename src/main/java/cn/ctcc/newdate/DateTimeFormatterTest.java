package cn.ctcc.newdate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zk
 * @Date: 2019/4/9 10:16
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class DateTimeFormatterTest {


    public static void main(String[] args) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String str="2019-04-09 10:34:00";

        Callable<LocalDateTime> task=()->LocalDateTime.parse(str,dateTimeFormatter);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDateTime>> list = new ArrayList<>();
        for(int i=0;i<100;i++){

            list.add(pool.submit(task));
        }

        pool.shutdown();

        list.forEach(l-> {
            try {
                System.out.println(l.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
