package seu.qz.qzserver.test;

public class ChildClass extends ParentClass{

    @Override
    public void test() {
        super.test();
        System.out.println("child");
    }
}
