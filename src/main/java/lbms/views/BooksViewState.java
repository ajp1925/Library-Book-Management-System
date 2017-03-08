package lbms.views;

import lbms.controllers.ViewController;

public class BooksViewState implements State {
    private boolean SYSTEM_STATUS;

    public BooksViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("search)      Search for a book");
        System.out.println("browse)      Show all available books");
        System.out.println("checkout)    Borrow a book");
        System.out.println("checkin)     Return a book");
        System.out.println("return)      Return to main menu");
    }

    @Override
    public void onEnter() {

    }
     @Override
    public void change(String state) {
         switch (state) {
             case "search": break;
             case "browse": break;
             case "checkout": break;
             case "checkin": break;
             case "return": ViewController.setState(new DefaultViewState(SYSTEM_STATUS)); break;
             default:
                 System.out.println("Command not found\n");
                 this.init();
         }
    }
}
