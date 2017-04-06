package lbms.views.CLI.viewstate;

import lbms.LBMS;
import lbms.models.Visitor;
import lbms.views.CLI.CLIView;

/**
 * UserListViewState class for views package.
 * @author Team B
 */
public class UserListViewState implements State {

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

        CLIView.setState(new UserMenuViewState());
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
