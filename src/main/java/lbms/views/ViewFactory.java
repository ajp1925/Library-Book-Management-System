package lbms.views;

import lbms.LBMS;
import lbms.views.api.APIView;
import lbms.views.gui.GUIView;

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
        switch (type) {
            case API:
                APIView.getInstance().run();
                break;
            case GUI:
                new GUIView().run();
                break;
            default:
                new GUIView().run();
                break;
        }
    }
}
