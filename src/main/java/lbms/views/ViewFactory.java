package lbms.views;

import lbms.LBMS;
import lbms.views.API.APIView;
import lbms.views.CLI.CLIView;
import lbms.views.GUI.GUIView;

import java.time.LocalTime;

/**
 * Controller for the views package.
 * @author Team B
 */
public class ViewFactory {
    private final static LocalTime OPEN_TIME = LBMS.OPEN_TIME;
    private final static LocalTime CLOSE_TIME = LBMS.CLOSE_TIME;

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

    public static void LibraryClose() {
        LBMS.LibraryClose();
    }

    public static LocalTime getCloseTime() {
        return CLOSE_TIME;
    }

    public static LocalTime getOpenTime() {
        return OPEN_TIME;
    }
}
