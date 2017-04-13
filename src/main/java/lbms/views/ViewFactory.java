package lbms.views;

import lbms.LBMS;
import lbms.views.API.APIView;
import lbms.views.GUI.GUIView;

/**
 * Controller for the views package.
 * @author Team B
 */
public class ViewFactory {

    /**
     * Starts up the LBMS by creating the user interface type and running it.
     * @param type: the specified type of interface to use
     */
    public static void start(LBMS.StartType type) {
        View ui;

        switch (type) {
            case API:
                ui = APIView.getInstance();
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

}
