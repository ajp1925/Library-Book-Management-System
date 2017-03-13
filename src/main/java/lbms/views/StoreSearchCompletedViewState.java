package lbms.views;

import lbms.controllers.ViewController;

/**
 * Created by 15bar on 3/12/2017.
 */
public class StoreSearchCompletedViewState implements State{
    private boolean SYSTEM_STATUS;

    public StoreSearchCompletedViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the StoreSearchCompleted State
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("purchase)    Buy a book for the library from these search results");
        System.out.println("search)      Search the store again");
        System.out.println("return)      Return to main menu");
    }

    @Override
    public void onEnter() { }

    /**
     * Changes state based on user input
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
        switch(state) {
            case "purchase":
                ViewController.setState(new PurchaseBookViewState(SYSTEM_STATUS));
                break;
            case "search":
                ViewController.setState(new StoreSearchViewState(SYSTEM_STATUS));
                break;
            case "return":
                ViewController.setState(new DefaultViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }

    }
}
