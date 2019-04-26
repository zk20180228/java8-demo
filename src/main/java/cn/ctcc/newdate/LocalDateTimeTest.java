package cn.ctcc.newdate;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @Author: zk
 * @Date: 2019/4/8 15:58
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class LocalDateTimeTest {

    /**
     * LocalDate、LocalTime、LocalDateTime
     * UTC格式
     */
    @Test
    public void test01(){

        LocalDateTime dateTime01 = LocalDateTime.now();
        //2019-04-08T15:59:39.945
        System.out.println(dateTime01);

        LocalDateTime dateTime02 = LocalDateTime.of(2019, 3, 8, 16, 1, 10);
        //2019-03-08T16:01:10
        System.out.println(dateTime02);

        LocalDateTime dateTime03 = dateTime02.plusYears(1);
        //2020-03-08T16:01:10
        System.out.println(dateTime03);

        LocalDateTime dateTime04 = dateTime02.minusMonths(1);
        //2019-02-08T16:01:10
        System.out.println(dateTime04);

        System.out.println(dateTime04.getYear());
        System.out.println(dateTime04.getMonthValue());
        System.out.println(dateTime04.getDayOfMonth());
        System.out.println(dateTime04.getHour());
        System.out.println(dateTime04.getMinute());
        System.out.println(dateTime04.getSecond());
    }

    /**
     * Instant:时间戳，（使用unix元年，1970年1月1日 00:00:00所经历的毫秒值）
     * 默认使用UTC市区
     */
    @Test
    public void test02(){

        Instant instant = Instant.now();
        //2019-04-08T08:10:23.100Z
        System.out.println(instant);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        //2019-04-08T16:12:16.734+08:00
        System.out.println(offsetDateTime);

        //得到当前时间的毫秒值
        long l = instant.toEpochMilli();
        System.out.println(l);

        //根据毫秒值创建Instant实例
        Instant instant1 = Instant.ofEpochMilli(l);

        System.out.println(instant1);

    }

    /**
     * Duration:用于计算两个时间的间隔
     * Period:用于计算两个日期之间的间隔
     */
    @Test
    public void test03(){


        Duration d1 = Duration.between(Instant.now(), Instant.now());
        //0
        System.out.println(d1.getSeconds());

        LocalDateTime ld01 = LocalDateTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDateTime ld02 = LocalDateTime.now();
        Duration d2 = Duration.between(ld01, ld02);
        //1
        System.out.println(d2.getSeconds());

        LocalDate start = LocalDate.of(2020, 5, 18);
        LocalDate end = LocalDate.of(2020, 4, 18);

        Period p = Period.between(end, start);
        //0
        System.out.println(p.getYears());
        //0
        System.out.println(p.getDays());
        //1
        System.out.println(p.getMonths());
    }


    /**
     * TemporalAdjuster:时间矫正器
     */
    @Test
    public void test04(){

        LocalDateTime now = LocalDateTime.now();
        //2019-04-08T16:50:07.435
        System.out.println(now);

        //把月的日期修改为10
        LocalDateTime dateTime = now.withDayOfMonth(10);
        //2019-04-10T16:50:07.435
        System.out.println(dateTime);
        //返回下一个周日的时间，即本周日的时间
        LocalDateTime ld = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ld);

        //自定义下一个工作日
        LocalDateTime d5 = now.with(t -> {

            LocalDateTime tt = (LocalDateTime) t;
            DayOfWeek dayOfWeek = tt.getDayOfWeek();
            if(DayOfWeek.FRIDAY.equals(dayOfWeek)){
                return tt.plusDays(3);
            }else if(DayOfWeek.SATURDAY.equals(dayOfWeek)){
                return tt.plusDays(2);
            }else{
                return tt.plusDays(1);
            }
        });
        //2019-04-09T17:01:11.773
        System.out.println(d5);
    }

    /**
     * DateTimeFormatter:解析或格式化日期或时间
     */
    @Test
    public void test05(){

        DateTimeFormatter dateTimeFormatter01 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime now = LocalDateTime.now();
        //2019-04-08T17:05:37.647
        System.out.println(now);

        String str01 = dateTimeFormatter01.format(now);
        //2019-04-08T17:05:12.668
        System.out.println(str01);

        DateTimeFormatter dateTimeFormatter02 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //2019-04-08 17:06:50
        System.out.println(dateTimeFormatter02.format(now));
        String str02="2019-04-08 17:06:50";

        LocalDateTime tmp = LocalDateTime.parse(str02, dateTimeFormatter02);
        //2019-04-08T17:06:50
        System.out.println(tmp);
    }

    @Test
    public void test06(){
        //获取所有可用时区id
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        //  Asia/Shanghai
        zoneIds.forEach(System.out::println);
    }


    /**
     * ZonedDateTime,ZonedDate,ZonedTime:带时区的日期或者时间
     */
    @Test
    public void test07(){

        LocalDateTime now1 = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        //2019-04-08T17:17:24.763
        System.out.println(now1);

        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        //2019-04-08T17:17:24.764+08:00[Asia/Shanghai]
        System.out.println(now2);
    }


}
