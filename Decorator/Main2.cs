/// Another silly example
using System;

public abstract class BasePizza
{
    protected double price;

    public virtual double getPrice() => price;
}

public abstract class BasePizzaDecorator : BasePizza
{

    private BasePizza pizza;
    
    public BasePizzaDecorator(BasePizza pizza)
    {
        this.pizza = pizza;
    }

    public override double getPrice() => this.pizza.getPrice() + this.price;
}

// Pizza types
public class Margarita : BasePizza
{
    public Margarita()
    {
        this.price = 9.99;
    }
}

public class Gourmet : BasePizza
{
    public Gourmet()
    {
        this.price = 9.99;
    }
}

public class ExtraCheeseDecorator : BasePizzaDecorator
{

    public ExtraCheeseDecorator(BasePizza pizza) : base(pizza)
    {
        this.price = 0.99;
    }
}

public class ExtraMushroomsDecorator : BasePizzaDecorator
{
    public ExtraMushroomsDecorator(BasePizza pizza) : base(pizza)
    {
        this.price = 1.99;
    }
}

public class ExtraSalamiDecorator : BasePizzaDecorator
{
    public ExtraSalamiDecorator(BasePizza pizza) : base(pizza)
    {
        this.price = 3.99;
    }
}

public class Program
{
    public static void Main(string[] args)
    {
        BasePizza pizza = new Gourmet();
        Console.WriteLine($"The price for a simple gourmet pizza is {pizza.getPrice()}");

        BasePizzaDecorator cheeseDecorator = new ExtraSalamiDecorator(new ExtraMushroomsDecorator(new ExtraCheeseDecorator(pizza)));
        Console.WriteLine("The price for a simple gourmet pizza with cheese, mushroom, and salami topping is " + cheeseDecorator.getPrice());
        
        Console.ReadLine();
    }
}
