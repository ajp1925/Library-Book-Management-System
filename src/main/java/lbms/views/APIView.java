package lbms.views;

import lbms.controllers.CommandController;
import lbms.models.SystemDateTime;

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
        int initial = 0;

        do {
            System.out.print("> ");
            input = s.nextLine();
            if(SystemDateTime.getInstance(null).getTime().isAfter(ViewFactory.OPEN_TIME) &&
                    SystemDateTime.getInstance(null).getTime().isBefore(ViewFactory.CLOSE_TIME)) {
                // Check if library just opened or system start
                if(initial == 0 || initial == 1) {
                    initial = 2;
                }
                System.out.println(CommandController.processRequest(true, input));
            }
            else {
                // Check if library just closed or system start
                if(initial == 0 || initial == 2) {
                    ViewFactory.LibraryClose();
                    initial = 1;
                }
                System.out.println(CommandController.processRequest(false, input));
            }

        } while(!input.matches("(?i)exit|quit"));

        s.close();
    }
}
