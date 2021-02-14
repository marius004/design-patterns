/*
 * The following example uses the observer design pattern to implement an airport baggage claim information system. A BaggageInfo class provides information about arriving flights and the carousels where baggage from each flight is available for pickup. It is shown in the following example
 * https://docs.microsoft.com/en-us/dotnet/standard/events/observer-design-pattern
 */

using System;
using System.Collections.Generic;

public struct BaggageInfo
{
    public int FlightNo  { get; private set; }
    public string Origin { get; private set;  }
    public int  Carousel { get; private set; }

    internal BaggageInfo(int flightNo, string origin, int carousel)
    {
        FlightNo = flightNo;
        Origin = origin; 
        Carousel = carousel;
    }
}

// A BaggageHandler class is responsible for receiving information about arriving flights and baggage claim carousels.
//  Internally, it maintains two collections:
//      *observers - A collection of clients that will receive updated information.
//      *flights - A collection of flights and their assigned carousels.

public class BaggageHandler : IObservable<BaggageInfo>
{
    private List<IObserver<BaggageInfo>> _observers;
    private List<BaggageInfo> flights;

    public BaggageHandler()
    {
        _observers = new List<IObserver<BaggageInfo>>();
        flights = new List<BaggageInfo>();
    }

    public IDisposable Subscribe(IObserver<BaggageInfo> observer)
    {
        if (!_observers.Contains(observer))
        {
            _observers.Add(observer);
            Notify(observer);
        }

        return new Unsubscriber<BaggageInfo>(_observers, observer);
    }

    private void Notify(BaggageInfo baggageInfo)
    {
        foreach(var observer in _observers)
            observer.OnNext(baggageInfo);
    }

    private void Notify(IObserver<BaggageInfo> observer)
    {
        foreach (var flight in flights)
            observer.OnNext(flight);
    }
    
    // Called to indicate that all the baggage is now unloaded.
    public void BaggageStatus(int flightNo)
    {
        BaggageStatus(flightNo, string.Empty, 0);
    }

    public void BaggageStatus(int flighNo, string origin, int carousel)
    {
        var info = new BaggageInfo(flighNo, origin, carousel);

        // Carousel is assigned, so add new info object to list.
        if (carousel > 0 && !flights.Contains(info))
        {
            flights.Add(info);
            Notify(info);
            
        } else if (carousel == 0){
            
            var flightsToRemove = new List<BaggageInfo>();

            foreach (var flight in flights){
                
                if (info.FlightNo == flight.FlightNo){
                    
                    flightsToRemove.Add(flight);
                    Notify(info);
                }
            }

            foreach (var flight in flightsToRemove)
                flights.Remove(flight);
            
            flightsToRemove.Clear();
        }
    }

    public void LastBaggageClaimed()
    {
        foreach (var observer in _observers)
            observer.OnCompleted();

        _observers.Clear();
    }
}

public class Unsubscriber<T> : IDisposable 
{
    private List<IObserver<BaggageInfo>> _observers;
    private IObserver<BaggageInfo> _observer;

    internal Unsubscriber(List<IObserver<BaggageInfo>> observers, IObserver<BaggageInfo> observer)
    {
        this._observers = observers;
        this._observer = observer;
    }

    public void Dispose()
    {
        if (_observers.Contains(_observer))
            _observers.Remove(_observer);
    }
}

public class ArrivalsMonitor : IObserver<BaggageInfo>
{
    private string name;
    private List<string> flightsInfo = new List<string>();
    private IDisposable cancellation;
    private string fmt = "{0,-20} {1, 5}  {2, 3}";

    public ArrivalsMonitor(string name)
    {
        if (String.IsNullOrEmpty(name))
            throw new ArgumentNullException("The obseerver must be assigned a name");

        this.name = name;
    }

    public virtual void Subscribe(BaggageHandler provider)
    {
        cancellation = provider.Subscribe(this);
    }

    public virtual void Unsubscribe()
    {
        cancellation.Dispose();
        flightsInfo.Clear();
    }

    public void OnCompleted()
    {
       flightsInfo.Clear(); 
    }

    public void OnError(Exception error)
    {
       // No implementation  
    }
    
    //Update Information 
    public void OnNext(BaggageInfo info)
    {
        bool updated = false;
        
        // Flight has unloaded its baggage; remove from the monitor.
        if (info.Carousel == 0){
            
            var flightsToRemove = new List<string>();
            string flightNo = String.Format("{0, 5}", info.FlightNo);

            foreach (var flightInfo in flightsInfo){
                if (flightInfo.Substring(21, 5).Equals(flightNo)){
                    flightsToRemove.Add(flightInfo);
                    updated = true;
                }
            }

            foreach (var flight in flightsToRemove)
                flightsInfo.Remove(flight);
            
            flightsToRemove.Clear();
            
        } else {
            
            /// Add flight if it does not exist in the collection
            string flightInfo = String.Format(fmt, info.Origin, info.FlightNo, info.Carousel);

            if (!flightsInfo.Contains(flightInfo)){
                flightsInfo.Add(flightInfo);
                updated = true;
            }
        }

        if (updated){
            
            flightsInfo.Sort();
            Console.WriteLine("Arrivals information from {0}", this.name);

            foreach (var information in flightsInfo)
                Console.WriteLine(information);

            Console.WriteLine();
        }
    }
    
}


class Program
{
    public static void Main(string[] args)
    {
        BaggageHandler provider   = new BaggageHandler();
        ArrivalsMonitor observer1 = new ArrivalsMonitor("BaggageClaimMonitor");
        ArrivalsMonitor observer2 = new ArrivalsMonitor("SecurityExit");
        
        provider.BaggageStatus(712, "Detroit", 3);
        observer1.Subscribe(provider);
        
        provider.BaggageStatus(712, "Kalamazoo", 3);
        provider.BaggageStatus(400, "New York-Kennedy", 1);
        provider.BaggageStatus(712, "Detroit", 3);
        observer2.Subscribe(provider);
        provider.BaggageStatus(511, "San Francisco", 2);
        provider.BaggageStatus(712);
        observer2.Unsubscribe();
        provider.BaggageStatus(400);
        provider.LastBaggageClaimed();
    }
}
