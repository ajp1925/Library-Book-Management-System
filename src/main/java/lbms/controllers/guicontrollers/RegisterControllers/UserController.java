package lbms.controllers.guicontrollers.RegisterControllers;

import java.util.ArrayList;

/**
 * Created by Chris on 4/6/17.
 */
public interface UserController {

    boolean isNew();

    ArrayList<String> getFields();

    void fail();

    void clearError();
}
