package cn.ctcc.lambda;

/**
 * @Author: zk
 * @Date: 2019/4/3 17:12
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class SalaryPredicate implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary()<5000;
    }
}
