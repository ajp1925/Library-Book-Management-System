package lbms.views.API;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.View;

import java.util.Scanner;

/**
 * Created by Chris on 3/28/17.
 */
public class APIView implements View {
    private static APIView instance = null;

    private APIView() {}

    public static APIView getInstance() {
        if(instance == null) {
            instance = new APIView();
        }
        return instance;
    }

    public void run() {
        Scanner s = new Scanner(System.in);
        String input;

        do {
            System.out.print("> ");
            input = s.nextLine();
            System.out.println(new ProxyCommandController().processRequest(input));
        } while(!input.matches("(?i)exit|quit"));

        s.close();
    }
}
