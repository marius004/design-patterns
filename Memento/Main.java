import java.util.ArrayList;
import java.util.List;

public class Caretaker {

    private List<Memento>  history;
    private int currentState;

    public Caretaker() {
        this.history = new ArrayList<>();
        currentState = -1;
    }

    public void addMemento(Memento memento) {
        this.history.add(memento);
        currentState = this.history.size() - 1;
    }

    public Memento getMemento(int index) {
        return history.get(index);
    }

    public Memento undo() {

        System.out.println("Undoing state...");

        if(currentState <= 0) {
            currentState = 0;
            return history.get(currentState);
        }

        currentState--;
        return history.get(currentState);
    }

    public Memento redo() {

        System.out.println("Redoing state...");

        if(currentState >= history.size() - 1) {
            currentState = history.size() - 1;
            return history.get(currentState);
        }

        currentState++;
        return history.get(currentState);
    }
}

public class Memento {

    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

public class Originator {

    private String article;

    public Originator() {}

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticle() {
        return this.article;
    }

    public Memento save() {
        return new Memento(article);
    }

    public void restore(Memento memento) {
        this.article = memento.getState();
    }
}

public class Main {

    public static void main(String[] args) {

        Originator originator = new Originator();
        Caretake caretaker = new Caretaker();

        originator.setArticle("State 1");
        caretaker.addMemento(originator.save());
        printState(originator);

        originator.setArticle("State 2");
        caretaker.addMemento(originator.save());
        printState(originator);

        originator.restore(caretaker.undo());
        printState(originator);

        originator.restore(caretaker.redo());
        printState(originator);

        for(int i = 3;i <= 6;++i) {
            originator.setArticle("State " + i);
            caretaker.addMemento(originator.save());
        }

        printState(originator);

        originator.restore(caretaker.undo());
        originator.restore(caretaker.undo());
        originator.restore(caretaker.undo());

        printState(originator);
    }

    public static void printState(Originator originator) {
        System.out.println("The current state: " + originator.getArticle());
    }
}
