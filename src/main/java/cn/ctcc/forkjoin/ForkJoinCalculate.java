package cn.ctcc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: zk
 * @Date: 2019/4/9 10:41
 * @Description:
 * Fork-Join:分支合并框架，工作窃取模式
 * @Modified:
 * @version: V1.0
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {


    private static final long serialVersionUID = -5786947616388669790L;

    private long start;
    private long end;
    /**
     * 临界值(拆分阈值threshold)
     */
    private static final long THRESHOLD=10000L;

    public ForkJoinCalculate(long start,long end){
        this.start=start;
        this.end=end;
    }

    @Override
    protected Long compute() {

        long length=end-start;
        if(length<=THRESHOLD){
            long sum=0;
            for(long i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }else{
            //拆分
            long middle=(start+end)/2;
            ForkJoinCalculate left = new ForkJoinCalculate(start,middle);
            //拆分，并将子任务压入线程队列
            left.fork();

            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            return left.join()+right.join();
        }

    }




}
