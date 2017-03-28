package lbms.views.viewstate;

import lbms.LBMS;
import lbms.views.CLIView;
import lbms.models.Visitor;

/**
 * UserListViewState class for views package.
 * @author Team B
 */
public class UserListViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for UserListViewState class.
     * @param SYSTEM_STATUS: the status of the system
     */
    UserListViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the state.
     */
    @Override
    public void init() {
        System.out.println();

        if (LBMS.getVisitors().isEmpty()) {
            System.out.println("No users are registered in the system.");
        }
        else {
            for(Visitor visitor : LBMS.getVisitors().values()) {
                System.out.println(String.format("Visitor ID: %d\n\tName: %s\n\tAddress: %s\n\tPhone: %s\n",
                        visitor.getVisitorID(), visitor.getName(), visitor.getAddress(), visitor.getPhoneNumber()));
            }
        }

        CLIView.setState(new UserMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation for this method.
     */
    @Override
    public void onEnter() {}

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}
}
