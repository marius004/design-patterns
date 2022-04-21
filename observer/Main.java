import java.util.ArrayList;
import java.util.List;

public class Main {
    public interface IObserver {
        void update();
    }

    public interface IDisplay {
        void display();
    }

    public interface IObservable {
        void registerObserver(IObserver observer);
        void removeObserver(IObserver observer);
        void notifyObservers();
    }

    public static class PhoneDisplay implements IDisplay, IObserver {
        private WeatherStation station;

        private double temperature; 
        private double humidity;
        private double pressure; 

        public PhoneDisplay(WeatherStation station) {
            this.station = station;
            update();
        }

        @Override
        public void update() {
            this.temperature = station.getTemperature();
            this.humidity = station.getHumidity();
            this.pressure = station.getPressure(); 
        }

        @Override
        public void display() {
            System.out.println("---------PhoneDisplay---------");
            System.out.println("Temperature: " + temperature);
            System.out.println("Humidity: " + humidity);
            System.out.println("Pressure: " + pressure);
            System.out.println("------------------------------");
        }
    }

    public static class WindowDisplay implements IDisplay, IObserver {
        private WeatherStation station;

        private double temperature; 
        private double humidity;
        private double pressure; 

        public WindowDisplay(WeatherStation station) {
            this.station = station;
            update();
        }

        @Override
        public void update() {
            this.temperature = station.getTemperature();
            this.humidity = station.getHumidity();
            this.pressure = station.getPressure(); 
        }

        @Override
        public void display() {
            System.out.println("---------WindowDisplay--------");
            System.out.println("Temperature: " + temperature);
            System.out.println("Humidity: " + humidity);
            System.out.println("Pressure: " + pressure);
            System.out.println("------------------------------");
        }
    }

    public static class WeatherStation implements IObservable {
        private double temperature; 
        private double humidity;
        private double pressure; 

        private List<IObserver> observers; 

        public WeatherStation(double temperature, double humidity, double pressure) {
            this.observers = new ArrayList<>();

            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
        }

        @Override
        public void registerObserver(Main.IObserver observer) {
            this.observers.add(observer);
        }

        @Override
        public void removeObserver(Main.IObserver observer) {
            this.observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for(IObserver observer : observers)
                observer.update();
        }

        @Override
        public String toString() {
            return "WeatherStation [temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + "]";
        }

        public double getTemperature() {
            return this.temperature;
        }
        
        public double getHumidity() {
            return this.humidity;
        }

        public double getPressure() {
            return this.pressure;
        }

        public void handleStateChange() {
            notifyObservers();
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
            handleStateChange();
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
            handleStateChange();
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
            handleStateChange();
        }

        public void setMeasurements(double temperature, double humidity, double pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            handleStateChange();
        }
    }

    public static void main(String[] args) {
        WeatherStation station = new WeatherStation(10, 20, 30);
        System.out.println(station);

        PhoneDisplay phoneDisplay = new PhoneDisplay(station);
        WindowDisplay windowDisplay = new WindowDisplay(station);

        station.registerObserver(phoneDisplay);
        station.registerObserver(windowDisplay);

        phoneDisplay.display();
        windowDisplay.display();

        station.setMeasurements(1000000, 0, 0);
        System.out.println(station);

        phoneDisplay.display();
        windowDisplay.display();
    }
}
