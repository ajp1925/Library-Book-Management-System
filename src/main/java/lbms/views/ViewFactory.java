package lbms.views;

import lbms.LBMS;

import java.time.LocalTime;

/**
 * Controller for the views package.
 * @author Team B
 */
public class ViewFactory {
    final static LocalTime OPEN_TIME = LBMS.OPEN_TIME;
    final static LocalTime CLOSE_TIME = LBMS.CLOSE_TIME;

    public static void start(String arg) {
        View ui;

        switch (arg) {
            case LBMS.API:
                ui = APIView.getInstance();
                break;
            case LBMS.CLI:
                ui = CLIView.getInstance();
                break;
            case LBMS.GUI:
                ui = new GUIView();
                break;
            default:
                ui = new GUIView();
                break;
        }

        ui.run();
    }

    static void LibraryClose() {
        LBMS.LibraryClose();
    }
}
