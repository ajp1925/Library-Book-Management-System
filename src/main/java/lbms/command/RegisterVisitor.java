package lbms.command;

import lbms.API;
import lbms.Visitor;

/**
 * RegisterVisitor class that calls the API to register a visitor in the system.
 * @author Team B
 */
public class RegisterVisitor implements Command {

    private Visitor visitor;

    /**
     * Constructor for RegisterVisitor class.
     * @param visitor: the visitor to be registered
     */
    public RegisterVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Executes the registration of a visitor.
     */
    @Override
    public void execute() {
        API.registerVisitor(visitor);
    }

}
