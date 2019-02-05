/* file: WindowController.java
 *
 *  Authors:
 *
 *      Alex Viznytsya
 *  Date:
 *
 *      10/26/2017
 *
 *  About:
 *
 *      This class create and initialize window for game.
 *
 */
package Controller;

import View.*;
import Model.*;

public class WindowController {

    // Class properties:
    private WindowView windowView;
    private SettingModel settingModel;

    // Default constructor:
    WindowController(GameController gameController) {
        this.windowView = new WindowView();
        this.settingModel = gameController.getSettingModel();
    }

    // Getter methods:
    public WindowView getWindowView() {
        return this.windowView;
    }

    // Other methodsl:
    public void initialize() {
        this.windowView.initialize(settingModel.getWindowWidth(), this.settingModel.getWindowHeight(), this.settingModel.getWindowTitle());
    }
}
