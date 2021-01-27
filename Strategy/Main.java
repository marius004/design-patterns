/// https://www.youtube.com/watch?v=v9ejT8FO-7I&list=PLrhzvIcii6GNjpARdnO4ueTUAVR9eMBpc&index=1&ab_channel=ChristopherOkhravi
/// https://www.geeksforgeeks.org/strategy-pattern-set-1/
/// https://www.geeksforgeeks.org/strategy-pattern-set-2/

//// intefaces 
public interface IDisplayStrategy {
    void display();
}

public interface IFlyStrategy {
    void fly();
}

public interface IQuackStrategy {
    void quack();
}

/// concrete implementations for the three interfaces
public class DefaultFlyStrategy implements IFlyStrategy {

    @Override
    public void fly() {
        System.out.println("I am flying");
    }
}

public class NoFlyStrategy implements IFlyStrategy {

    @Override
    public void fly() {
        System.out.println("Sorry I can't fly");
    }
}

public class DefaultQuackStrategy implements IQuackStrategy {

    @Override
    public void quack() {
        System.out.println("Default Quack!!");
    }
}

public class NoQuackStrategy implements IQuackStrategy {

    @Override
    public void quack() {
        System.out.println("Sorry I can't quack!!");
    }
}

public class DisplayAsGraphicStrategy implements IDisplayStrategy {
    
    @Override
    public void display() {
        System.out.println("Display as Graphic");
    }
}

public class DisplayAsTextStrategy implements IDisplayStrategy {

    @Override
    public void display() {
        System.out.println("Display as text");
    }
}

//// Class implementation 
public class Duck {

    private IDisplayStrategy displayStrategy;
    private IFlyStrategy flyStrategy;
    private IQuackStrategy quackStrategy;

    public Duck(IDisplayStrategy displayStrategy,
                IFlyStrategy flyStrategy,
                IQuackStrategy quackStrategy) {
        this.displayStrategy = displayStrategy;
        this.flyStrategy = flyStrategy;
        this.quackStrategy = quackStrategy;
    }

    public void fly() {
        this.flyStrategy.fly();
    }

    public void quack() {
        this.quackStrategy.quack();
    }

    public void display() {
        this.displayStrategy.display();
    }
}

/// Main
public class Main {

    public static void main(String[] args) {

        Duck duck1 = new Duck(new DisplayAsTextStrategy(),
                              new DefaultFlyStrategy(),
                              new NoQuackStrategy());

        duck1.display();
        duck1.fly();
        duck1.quack();
    }
}