package lbms.command;

/**
 * Created by anthony on 3/5/17.
 */
public class Borrow implements Command {

    private int visitorID;
    private int id;

    public Borrow(int visitorID, int id) {
        this.visitorID = visitorID;
        this.id = id;
    }

    public void execute() {
        // TODO Edward
    }

}
