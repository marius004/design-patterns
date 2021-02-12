using System;
using System.Collections.Generic;

struct WeatherData
{
    public string Name { get; private set; }
    public int Value { get; private set; }

    public WeatherData(string name, int value)
    {
        this.Name = name;
        this.Value = value;
    }

    public override string ToString() => Name + ":" + Value;
}

class WeatherStation : IObservable<WeatherData>
{

    private List<IObserver<WeatherData>> _observers;

    public void AddData(WeatherData data)
    {
        Notify(data);
    }
    
    public WeatherStation()
    {
        _observers = new List<IObserver<WeatherData>>();
    }

    public IDisposable Subscribe(IObserver<WeatherData> observer)
    {
        _observers.Add(observer);
        return new Unsubscriber(_observers, observer);
    }

    public void PrintSubscribers() => Console.WriteLine(string.Join("\n", _observers));
    
    public void Notify(WeatherData data)
    {
        foreach (var observer in _observers)
            observer.OnNext(data);
    }

    private class Unsubscriber : IDisposable
    {

        private ICollection<IObserver<WeatherData>> _observers;
        private IObserver<WeatherData> _observer;
        
        public Unsubscriber(ICollection<IObserver<WeatherData>> observers, IObserver<WeatherData> observer)
        {
            _observers = observers;
            _observer = observer;
        }
        
        public void Dispose()
        {
            if (_observers.Contains(_observer))
                _observers.Remove(_observer);
        }
    }
}

class WeatherDataPrinter : IObserver<WeatherData>
{

    public void OnCompleted() {}

    public void OnError(Exception error)
    {
        Console.WriteLine("An Exception occured");
    }
    
    public void OnNext(WeatherData value) => Console.WriteLine(value);
}

class WeatherDataAggregator : IObserver<WeatherData>
{
    private int sum = 0;
    private int n = 0;
    private int average = 0;
    
    public void OnCompleted() {}
    
    public void OnError(Exception error)
    {
        Console.WriteLine("An exception occured");
    }
    
    public void OnNext(WeatherData weatherData)
    {
        sum += weatherData.Value;
        n++;
        average = sum / n;
        Console.WriteLine("The running average is " + average);
    }

    public int getAverage() => average;
}

class Program
{
    public static void Main(string[] args)
    {
        // create the observable 
        WeatherStation station = new WeatherStation();

        // create the observers 
        WeatherDataPrinter printer = new WeatherDataPrinter();
        WeatherDataAggregator aggregator = new WeatherDataAggregator();

        var subscriber1 = station.Subscribe(printer); 
        var subscriber2 = station.Subscribe(aggregator);
       
        station.AddData(new WeatherData("Temperature", 100));
        station.AddData(new WeatherData("Temperature", 80));
        
        // subscriber1.Dispose();
        station.PrintSubscribers();

        Console.WriteLine(aggregator.getAverage());
    }
}
