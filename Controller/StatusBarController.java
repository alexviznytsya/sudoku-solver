/* file: StatusBarController.java
 *
 *  Authors:
 *
 *      Alex Viznytsya
 *
 *  Date:
 *
 *      10/26/2017
 *
 *  About:
 *
 *      This class initialize and provides controll for status
 *      bar in the bottom of the game window.
 */
package Controller;

import View.StatusBarView;
import View.WindowView;

public class StatusBarController {

    // Class properties:
    private WindowView windowView;
    private StatusBarView statusBarView;

    // Default constructor:
    StatusBarController(GameController gameController) {
        this.windowView = gameController.getWindowController().getWindowView();
        this.statusBarView = new StatusBarView();
    }

    // Other methods:
    public void initialize() {
        this.windowView.getMainContentPanel().add("StatusBar", this.statusBarView.initialize());

    }

    public void updateStatusMessage(String statusMessage) {
        this.statusBarView.updateStatusMessage(statusMessage);

    }

    public void updateStatusTitle(String statusTitle) {
        this.statusBarView.updateStatusTitle(statusTitle);
    }

    public void printGameInfoMessage(String message) {
        this.updateStatusTitle("Game Information:");
        this.updateStatusMessage(message);
    }

    public void printErrorMessage(String message) {
        this.updateStatusTitle("Error:");
        this.updateStatusMessage(message);
    }

    public void printAlgorithmUpdateMessage(String algorithm, int[] update) {
        this.updateStatusTitle(algorithm + " Algorithm result:");
        this.updateStatusMessage("Updated cell [" + (update[0] + 1) + ", " + (update[1] + 1) + "] with value " + update[2] + ".");
    }
}
