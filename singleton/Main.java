public class Main {
    
    public static class Singleton {
        private static Singleton Instance;

        private Singleton() {}

        public static Singleton GetInstance() {
            if (Instance == null)
                Instance = new Singleton();
            
            return Instance;
        }
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.GetInstance();
    }
}
