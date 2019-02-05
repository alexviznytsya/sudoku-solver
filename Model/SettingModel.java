/* file: SettingModel.java
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
 *     This class is basic setting of the game.
 *     Can be expandable if needed.
 *
 */
package Model;

public class SettingModel {

    // Class properties:
    private boolean saveWhenGameSolved;
    private int windowWidth;
    private int windowHeight;
    private String windowTitle;


    // Default constructor:
    public SettingModel() {
        this.saveWhenGameSolved = false;
        this.windowWidth = 325;
        this.windowHeight = 400;
        this.windowTitle = "Sudoku Solver";
    }


    // Getter methods:
    public boolean isSaveWhenGameSolved() {
        return this.saveWhenGameSolved;
    }

    public int getWindowWidth() {
        return this.windowWidth;
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public String getWindowTitle() {
        return this.windowTitle;
    }

    // Setter methods:
    public void setSaveWhenGameSolved(boolean saveWhenGameSolved) {
        this.saveWhenGameSolved = saveWhenGameSolved;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }
}
