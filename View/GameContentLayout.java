/* file: GameContentLayout.java
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
 *    This class is custom layout for game parts: gamebord, controlls and
 *    status bar.
 *
 */
package View;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

class GameContentLayout implements LayoutManager {

    // Class properties:
    private static final String GAME_BOARD = "GameBoard";
    private static final String GAME_CONTROLS = "GameControls";
    private static final String GAME_STATUS_BAR = "StatusBar";
    private Component gameBoard;
    private Component gameControls;
    private Component gameStatusBar;

    @Override
    public void addLayoutComponent(String name, Component comp) {
        if (GAME_BOARD.equals(name)) {
            gameBoard = comp;
        } else if (GAME_CONTROLS.equals(name)) {
            gameControls = comp;
        } else if (GAME_STATUS_BAR.equals(name)) {
            gameStatusBar = comp;
        } else {
            System.err.println("Cannot add " + name + "to window layout.");
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        if (comp == gameBoard) {
            gameBoard = null;
        } else if (comp == gameControls) {
            gameControls = null;
        } else if (comp == gameStatusBar) {
            gameStatusBar = null;
        } else {
            System.err.println("Component " + comp.getName() + " does not exist in window layout.");
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return minimumLayoutSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(325, 330);
    }

    @Override
    public void layoutContainer(Container parent) {

        if ((gameBoard != null) && gameBoard.isVisible()) {
            gameBoard.setBounds(5, 5, (int)((double)parent.getWidth() * 0.8), (int)((double)parent.getHeight() * 0.8));
        }

        if ((gameControls != null) && gameControls.isVisible()) {
            gameControls.setBounds(gameBoard.getWidth() + (int)((double)parent.getWidth() * 0.06), 5, (int)((double)parent.getWidth() * 0.11), (int)((double)parent.getHeight() * 0.97));
        }

        if ((gameStatusBar != null) && gameStatusBar.isVisible()) {
            gameStatusBar.setBounds(5, gameBoard.getHeight() + 10, (int)((double)parent.getWidth() * 0.8), (int)((double)parent.getHeight() * 0.15));
        }

    }
}
