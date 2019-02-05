/* file: GameController.java
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
 *      This class is base class for entire game. Here are initialized
 *      all game controllers, game views and models.
 *
 */
package Controller;

import Model.*;

import java.io.File;

public class GameController {

    // Class properties:
    private DataModel dataModel;
    private SettingModel settingModel;
    private WindowController windowController;
    private MenuBarController menuBarController;
    private GameBoardController gameBoardController;
    private ControlsController controlsController;
    private StatusBarController statusBarController;
    private String[] commandLineArgs = null;

    // Default constructor:
    public GameController(String[] args) {
        this.dataModel = new DataModel();
        this.settingModel = new SettingModel();
        this.windowController = new WindowController(this);
        this.controlsController = new ControlsController(this);
        this.statusBarController = new StatusBarController(this);
        this.gameBoardController = new GameBoardController(this);
        this.menuBarController = new MenuBarController(this);
        this.commandLineArgs = args;
    }

    // Getter methods:
    public DataModel getDataModel() {
        return this.dataModel;
    }

    public SettingModel getSettingModel() {
        return this.settingModel;
    }

    public WindowController getWindowController() {
        return this.windowController;
    }

    public MenuBarController getMenuBarController() {
        return this.menuBarController;
    }

    public GameBoardController getGameBoardController() {
        return this.gameBoardController;
    }

    public ControlsController getControlsController() {
        return this.controlsController;
    }

    public StatusBarController getStatusBarController() {
        return this.statusBarController;
    }

    // Other methods:
    public void start() {
        if(this.commandLineArgs.length > 1) {
            for (int argIndex = 0; argIndex < this.commandLineArgs.length; argIndex++) {
                if (commandLineArgs[argIndex].contains("-o") && commandLineArgs.length >= (argIndex + 1)) {
                    this.dataModel.setStoreFile(new File(commandLineArgs[argIndex + 1]));
                    break;
                }
            }
        } else if(this.commandLineArgs.length == 1) {
            String openFile = this.commandLineArgs[0];
            if(openFile.contains(".txt")) {
                this.dataModel.setLoadFile(new File(openFile));
                this.dataModel.setOpenFileOnLoad(true);
            }
        }
        this.buildGameGUI();
    }

    private void buildWindowFrame() {
        this.windowController.initialize();
    }

    private void buildMenuBar() {
        this.menuBarController.initialize();
    }

    private void buildStatusBar() {
        this.statusBarController.initialize();
    }

    private void buildGameBoard() {
        this.gameBoardController.initialize();
    }

    private void buildGameControls() {
        this.controlsController.initialize();
    }

    private void showGameWindow() {
        this.windowController.getWindowView().showWindow();
    }

    public void buildGameGUI() {
        this.buildWindowFrame();
        this.buildMenuBar();
        this.buildStatusBar();
        this.buildGameBoard();
        this.buildGameControls();
        this.showGameWindow();
    }

}
