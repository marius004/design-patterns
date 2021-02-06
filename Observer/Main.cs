using System;
using System.Collections.Generic;

class Program 
{
    static void Main(string[] args)
    {
        /// Create the observable(weather station)
        WeatherStation station = new WeatherStation();

        /// Create 2 observers
        WeatherDataPrinter    printer    = new WeatherDataPrinter();
        WeatherDataAggregator aggregator = new WeatherDataAggregator();
        
        /// Add the observers
        station.AddObserver(printer);
        station.AddObserver(aggregator);
        
        for(int i = 10;i <= 40;++i)
            station.AddData(new WeatherData("Temperature", i));

        Console.WriteLine("The final average is " + aggregator.getAvg());
    }
}

interface IWeatherDataObserver
{
    void Update(WeatherData wd);
}

interface IWeatherDataObservable
{
    void AddObserver(IWeatherDataObserver observer);
    
    /// These methods could have been used wihin the interface!!!
    
    // void RemoveObserver(IWeatherDataObserver observer);
    // void Notify();
}

class WeatherDataPrinter : IWeatherDataObserver
{
    public void Update(WeatherData wd)
    {
        Console.WriteLine(wd.ToString());
    }
}

class WeatherDataAggregator : IWeatherDataObserver
{
    private int sum = 0;
    private int N = 0;
    public double avg = 0;
    public void Update(WeatherData wd)
    {
        sum += wd.Value;
        N++;
        avg = (double)sum / N;
        Console.WriteLine("The Running average is " + getAvg());
    }

    public double getAvg()
    {
        return avg;
    }
}

// OBSERVABLE
//Single weather station
//      => Produces weather data(e.g temperature)

// OBSERVERS:
// Multiple consumers of weather station data
//      => Prints everything to screen
//      => Computes the average overtime

class WeatherStation : IWeatherDataObservable
{
    private ICollection<IWeatherDataObserver> observers = new List<IWeatherDataObserver>();

    public void AddData(WeatherData wd)
    {
        this.Notify(wd);
    }

    ///Observer pattern stuff below
    public void AddObserver(IWeatherDataObserver observer)
    {
        this.observers.Add(observer);
    }
    
    private void Notify(WeatherData data)
    {
        foreach (var o in observers)
            o.Update(data);
    }
}

class WeatherData
{
    public WeatherData(string name, int value)
    {
        Name = name;
        Value = value;
    }

    public string Name { get; private set; }
    public int Value { get; private set; }
    
    public override string ToString()
    {
        return this.Name + ": " + this.Value;
    }
}