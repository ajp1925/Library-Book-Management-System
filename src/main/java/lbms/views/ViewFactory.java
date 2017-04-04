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

    public static void start(LBMS.StartType type) {
        View ui;

        switch (type) {
            case API:
                ui = APIView.getInstance();
                break;
            case CLI:
                ui = CLIView.getInstance();
                break;
            case GUI:
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
