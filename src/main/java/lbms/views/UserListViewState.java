package lbms.views;

import lbms.LBMS;
import lbms.controllers.ViewController;
import lbms.models.Visitor;

/**
 * Created by Chris on 3/13/17.
 */
public class UserListViewState implements State {
    private boolean SYSTEM_STATUS;

    public UserListViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        System.out.println();

        if (LBMS.getVisitors().isEmpty()) {
            System.out.println("No users are registered in the system.");
        } else {
            for (Visitor visitor : LBMS.getVisitors().values()) {
                System.out.println(String.format("Visitor ID: %d\n\tName: %s\n\tAddress: %s\n\tPhone: %s\n",
                        visitor.getVisitorID(), visitor.getName(), visitor.getAddress(), visitor.getPhoneNumber()));
            }
        }

        ViewController.setState(new UserMenuViewState(SYSTEM_STATUS));
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void change(String state) {

    }
}
