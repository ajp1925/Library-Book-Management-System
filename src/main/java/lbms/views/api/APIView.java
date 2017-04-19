package lbms.views.api;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.View;

import java.util.Scanner;

/**
 * APIView class used for the api mode of the LBMS.
 * @author Team B
 */
public class APIView implements View {

    private static APIView instance = null;

    /**
     * Constructor for the APIView.
     */
    private APIView() {}

    /**
     * Gets the instance or creates a new one.
     * @return an instance of the APIView.
     */
    public static APIView getInstance() {
        if (instance == null) {
            instance = new APIView();
        }
        return instance;
    }

    /**
     * Runs the api mode of the LBMS.
     */
    public void run() {
        Scanner s = new Scanner(System.in);
        String input;

        do {
            System.out.print("> ");
            input = s.nextLine();
            System.out.println(new ProxyCommandController().processRequest(input));
        } while (!input.matches("(?i)exit|quit"));

        s.close();
    }
}
