package cn.ctcc.newdate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zk
 * @Date: 2019/4/8 17:21
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class DateFormatThreadLocal {


    public static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL=new ThreadLocal(){

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    public static  Date parse(String value) throws Exception{

        return THREAD_LOCAL.get().parse(value);
    }

    public static String format(Date date){

        return THREAD_LOCAL.get().format(date);
    }


}
