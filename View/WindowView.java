/* file: WindowView.java
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
 *    This class build main window of the game.
 *
 */
package View;

import Controller.*;


import javax.swing.WindowConstants;
import javax.swing.*;
import java.awt.*;

public class WindowView extends JFrame {

    // Class properties:
    private JPanel mainContentPanel;


    // Default constructor:

    public WindowView() {
        this.mainContentPanel = new JPanel();

    }

    // Getter methods:

    public JPanel getMainContentPanel() {
        return this.mainContentPanel;
    }

    // Setter methods:


    // Other methods:

    private void initWindowAndMenu(int width, int height, String title) {
        setMinimumSize(new Dimension(width, height));
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setResizable(false);
    }

    private void initMainGamePanel() {
        this.mainContentPanel.setLayout(new GameContentLayout());
        getContentPane().add(this.mainContentPanel);
    }

    public void initialize(int width, int height, String title) {
        this.initWindowAndMenu(width, height, title);
        this.initMainGamePanel();
    }

    public void redrawBoard() {
        this.mainContentPanel.revalidate();
    }


    public void showWindow() {
        pack();
        setVisible(true);
    }


}
