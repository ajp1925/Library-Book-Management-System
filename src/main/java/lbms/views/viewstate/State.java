package lbms.views.viewstate;

import java.io.IOException;

/**
 * Abstract representation of a views.
 *
 * Note: This is not an interface, as to ensure correct access modifiers.
 */
public interface State {

    /**
     * Updates the views. Should only be called internally.
     */
    void init();

    /**
     * Called every time the views is entered.
     */
    void onEnter();

    /**
     * Handle a command passed to the views
     * @param state: the command to handle
     */
    void change(String state);

    /**
     * Used to flush the console.
     */
    default void flush() {
        if(System.getProperty("os.name").startsWith("Windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            }
            catch(IOException e) {}
        }
        else {
            System.out.print("\033[H\033[2J");
        }
        System.out.flush();
    }

}