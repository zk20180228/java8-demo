package cn.ctcc.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zk
 * @Date: 2019/5/16 14:19
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class MyThreadLocalDateFormat {

    private static final MyThreadLocal<SimpleDateFormat> MY_THREAD_LOCAL =new MyThreadLocal(){

        @Override
        public Object init() {
            return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    public static Date parse(String value) throws Exception{

        return MY_THREAD_LOCAL.get().parse(value);
    }

    public static String format(Date date){

        return MY_THREAD_LOCAL.get().format(date);
    }



}
