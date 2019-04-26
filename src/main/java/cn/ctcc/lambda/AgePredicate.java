package cn.ctcc.lambda;

/**
 * @Author: zk
 * @Date: 2019/4/3 17:10
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class AgePredicate implements MyPredicate<Employee> {


    @Override
    public boolean test(Employee employee) {

        return employee.getAge()<35;
    }
}
