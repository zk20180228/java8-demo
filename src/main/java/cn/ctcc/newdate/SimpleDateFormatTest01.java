package cn.ctcc.newdate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zk
 * @Date: 2019/4/8 17:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class SimpleDateFormatTest01 {


    /**
     * SimpleDateFormat 是线程不安全的
     * Caused by: java.lang.NumberFormatException: For input string: ".404E2.404E2"
     * @param args
     */
    public static void main(String[] args) throws Exception{

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str="2019-04-09 9:38:30";

        Callable<Date> task = ()->simpleDateFormat.parse(str);

        @SuppressWarnings("AlibabaThreadPoolCreation")
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> listDate=new ArrayList<>();
        for(int i=0;i<20;i++){
            listDate.add(pool.submit(task));
        }

        pool.shutdown();

        listDate.forEach(e-> {
            try {
                System.out.println(e.get());
            } catch (Exception ex) {
               throw new RuntimeException(ex);
            }
        });


    }






}
