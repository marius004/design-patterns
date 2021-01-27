//https://stackabuse.com/the-builder-design-pattern-in-java/
public class SmartHome {

    /// a great deal of fields
    private String name;
    private int serialNumber;
    private String addressName;
    private int addressNumber;
    private String city;
    private String country;
    private String postalCode;
    private int light1PortNum;
    private int light2PortNum;
    private int door1PortNum;
    private int door2PortNum;

    private SmartHome(String name){
        this.name = name;
    }

    // Other methods in here

    public static class SmartHomeBuilder {

        private String name;
        private int serialNumber;
        private String addressName;
        private int addressNumber;
        private String city;
        private String country;
        private String postalCode;
        private int light1PortNum;
        private int light2PortNum;
        private int door1PortNum;
        private int door2PortNum;

        public SmartHomeBuilder(String name) {
            this.name = name;
        }

        public SmartHomeBuilder setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public SmartHomeBuilder setAddressNumber(int addressNumber) {
            this.addressNumber = addressNumber;
            return this;
        }

        public SmartHomeBuilder setAddressName(String addressName) {
            this.addressName = addressName;
            return this;
        }

        public SmartHomeBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public SmartHomeBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public SmartHomeBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public SmartHomeBuilder setLight1PortNum(int light1PortNum) {
            this.light1PortNum = light1PortNum;
            return this;
        }

        public SmartHomeBuilder setLight2PortNum(int light2PortNum) {
            this.light2PortNum = light2PortNum;
            return this;
        }

        public SmartHomeBuilder setDoor1PortNum(int door1PortNum) {
            this.door1PortNum = door1PortNum;
            return this;
        }

        public SmartHomeBuilder setDoor2PortNum(int door2PortNum) {
            this.door2PortNum = door2PortNum;
            return this;
        }

        public SmartHome build() {

            SmartHome instance = new SmartHome(this.name);

            instance.serialNumber = this.serialNumber;
            instance.addressName = this.addressName;
            instance.addressNumber = this.addressNumber;
            instance.city = this.city;
            instance.country = this.country;
            instance.postalCode = this.postalCode;
            instance.light1PortNum = this.light1PortNum;
            instance.light2PortNum = this.light2PortNum;
            instance.door1PortNum = this.door1PortNum;
            instance.door2PortNum = this.door2PortNum;

            return instance;
        }
    }

    public static void main(String[] args) {

        SmartHome smartHome = new SmartHome
                .SmartHomeBuilder("Smart Home")
                .setAddressName("Los Angeles")
                .setAddressNumber(123456789)
                .setCountry("USA")
                .setDoor1PortNum(8080)
                .setDoor2PortNum(5000)
                .build();
    }
}