package cn.ctcc.optional;

/**
 * @Author: zk
 * @Date: 2019/4/8 15:02
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class Man {

    private Godness godness;

    public Man(){

    }
    public Man(Godness godness){
        this.godness=godness;
    }

    public Godness getGodness() {
        return godness;
    }

    public void setGodness(Godness godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "Man{" +
                "godness=" + godness +
                '}';
    }
}
