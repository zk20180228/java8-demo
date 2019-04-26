package cn.ctcc.lambda;

/**
 * @Author: zk
 * @Date: 2019/4/3 15:04
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class Employee {

    private int id;

    private String name;

    private int age;

    private double salary;

    private Status status;


    public  Employee(){

    }

    public Employee(String name){
        this.name=name;
    }

    public Employee(String name,int age){
        this.name=name;
        this.age=age;
    }

    public Employee(String name,int age,double salary){
        this.name=name;
        this.age=age;
        this.salary=salary;
    }

    public Employee(int id,String name,int age,double salary){
        this.id=id;
        this.name=name;
        this.age=age;
        this.salary=salary;
    }

    public Employee(int id,String name,int age,double salary,Status status){
        this(id ,name, age ,salary);
        this.status=status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {

        final int prime=31;
        int result=1;
        result=prime*result+this.age;
        result=prime*result+this.id;
        result=prime*result+(this.name==null?0:this.name.hashCode());
        long temp;
        //根据IEEE 754浮点“双格式”位布局返回指定浮点值的表示。
        temp=Double.doubleToLongBits(this.salary);
        result=prime*result+(int)(temp^(temp>>>32));

        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(this.getClass()!=obj.getClass()){
            return false;
        }
        Employee other= (Employee) obj;
        if(this.name==null){
            if(other.name!=null){
                return false;
            }
        }else if(!this.name.equals(other.name)){
            return false;
        }

        if(Double.doubleToLongBits(this.salary)!=Double.doubleToLongBits(other.salary)){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    public enum Status{
        BUSY,FREE,VOCATION;
    }


}
