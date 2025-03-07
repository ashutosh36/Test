package src.kafka.javaNotes;

import javax.annotation.PostConstruct;
public class StaticBlocks {

    public static void main(String[] args) {


        StaticBlocks.staticMethod();

        StaticBlocks obj = new StaticBlocks();
        StaticBlocks obj2 = new StaticBlocks();

        obj.instanceMethod();
    }

    // Method annotated with @PostConstruct
    @PostConstruct //Will get exectuted only in spring context, not here
    public void postConstruct() {
        System.out.println("PostConstruct method executed");
    }

    // Instance method
    public void instanceMethod() {
        System.out.println("Instance method executed");
    }

    // Static block, This is executed first when the class is loaded by the JVM
    static {
        System.out.println("Static block executed");
    }

    // Constructor
    public StaticBlocks() {
        System.out.println("Constructor executed");
    }

    // Static method
    public static void staticMethod() {
        System.out.println("Static method executed");
    }

    // Instance initialization block, This runs after the constructor, as part of object creation.
    {
        System.out.println("Instance initialization block executed");
    }

    {
        System.out.println("Instance initialization block2 executed");
    }
}
