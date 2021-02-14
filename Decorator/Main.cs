/// Just another silly example. I am gonna come up with another better example
/// in the upcoming days
 
using System;

public interface Car
{
    void assemble();
}

public class BasicCar : Car
{
    public void assemble()
    {
        Console.WriteLine("Building a basic car");
    }
}

public class CarDecorator : Car
{
    protected Car car;

    public CarDecorator(Car car)
    {
        this.car = car;
    }

    public virtual void assemble()
    {
        this.car.assemble();
    }
}

public class SportsCarDecorator : CarDecorator
{
    public SportsCarDecorator(Car car) : base(car) {}

    public override void assemble()
    {
        base.assemble();
        Console.WriteLine("Adding the features of a sports car");
    }
}

public class LuxuryCarDecorator : CarDecorator
{
    public LuxuryCarDecorator(Car car) : base(car) {}

    public override void assemble()
    {
        base.assemble();
        Console.WriteLine("Adding the features of a luxury car");
    }
}

public class ElectricCarDecorator : CarDecorator
{
    public ElectricCarDecorator(Car car) : base(car) {}

    public override void assemble()
    {
        base.assemble();
        Console.WriteLine("Adding the features of an electric car");
    }
}

class Program
{
    public static void Main(string[] args)
    {
        Car car = new ElectricCarDecorator(
        new SportsCarDecorator(
        new LuxuryCarDecorator(
        new BasicCar()
        )
        )
        ); /// the syntax looks terrible

        car.assemble();
    }
}