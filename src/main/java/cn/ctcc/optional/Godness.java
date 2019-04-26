package cn.ctcc.optional;

/**
 * @Author: zk
 * @Date: 2019/4/8 15:02
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class Godness {

    private String name;

    public Godness(){

    }
    public Godness(String name){

        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Godness{" +
                "name='" + name + '\'' +
                '}';
    }
}
