package lbms.controllers;

import lbms.LBMS;
import lbms.views.APIView;
import lbms.views.CLIView;
import lbms.views.GUIView;
import lbms.views.View;

import java.time.LocalTime;

/**
 * Controller for the views package.
 * @author Team B
 */
public class ViewController {
    private static View ui = null;

    public final static LocalTime OPEN_TIME = LBMS.OPEN_TIME;
    public final static LocalTime CLOSE_TIME = LBMS.CLOSE_TIME;

    public static void start(String arg) {
        switch (arg) {
            case LBMS.API:
                ui = APIView.getInstance();
                break;
            case LBMS.CLI:
                ui = CLIView.getInstance();
                break;
            default:
                ui = new GUIView();
                break;
        }

        ui.run();
    }

    public static void LibraryClose() {
        LBMS.LibraryClose();
    }
}
