/// https://books.google.ro/books/about/Head_First_Design_Patterns.html?id=GGpXN9SMELMC&printsec=frontcover&source=kp_read_button&redir_esc=y#v=onepage&q&f=false 
/// https://www.youtube.com/watch?v=hUE_j6q0LTQ&list=PLrhzvIcii6GNjpARdnO4ueTUAVR9eMBpc&index=6&ab_channel=ChristopherOkhravi

/** VERSION 1 **/
public class Singleton {

    private static Singleton INSTANCE = new Singleton();

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

/** VERSION 2 **/
public class Singleton {

    private static Singleton INSTANCE;

    public static Singleton getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Singleton();

        return INSTANCE;
    }
}
/** VERSION 3 **/
public class Singleton {

    private static Singleton INSTANCE;

    public static synchronized Singleton getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Singleton();

        return INSTANCE;
    }
}

/** VERSION 4 **/
public class Singleton {

    private static volatile Singleton INSTANCE;

    public static Singleton getInstance() {

        if(INSTANCE == null) {
            synchronized (Singleton.class) {
                INSTANCE = new Singleton();
            }
        }

        return INSTANCE;
    }
}

/** PRACTICAL USAGE OF THE PATTERN **/

// GlobalLogger file
import java.io.*;

public class GlobalLogger {

    private static volatile GlobalLogger INSTANCE;

    private FileWriter writer;
    private boolean isClosed;
    private int counter = 0;

    private GlobalLogger() throws IOException {
        this.writer = new FileWriter("src/global.txt");
        this.isClosed = false;
        System.out.println("Created");
    }

    public static GlobalLogger getInstance() throws IOException {

        if(INSTANCE == null) {
            synchronized (GlobalLogger.class) {
                INSTANCE = new GlobalLogger();
            }
        }

        return INSTANCE;
    }

    public synchronized boolean log(Object printableObject, boolean flush) {

        if(!this.isClosed) {

            try {

                counter++;
                writer.write(counter + ": " + printableObject.toString() + "\n");

                if(flush)
                    flush();

                return true;

            } catch (IOException e) {

                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public synchronized boolean close() {
        if(isClosed)
           return false;

        try {
            isClosed = true;
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean flush() {
        try {
            this.writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

// Main file 
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        GlobalLogger logger = null;

        try {
            logger = GlobalLogger.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final GlobalLogger finalLogger = logger;

        Thread thread1 = new Thread(() -> {
            for(int i = 0;i < 100;++i) {
                String toPrint = "Ana are mere " + i + " Thread 1";
                finalLogger.log(toPrint, false);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for(int i = 0;i < 100;++i) {

                String toPrint = "Ana are mere " + i + " Thread 2";
                finalLogger.log(toPrint, false);

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.close();
    }
}