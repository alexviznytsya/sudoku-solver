/* file: StatusBarView.java
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
 *    This class creates status bar at the bottom of the game window.
 *
 */
package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StatusBarView {

    // Class properties:
    private JPanel statusBarPanel;
    private JLabel statusBarTitleLabel;
    private JLabel statusBarMessageLabel;


    // Default constructor:
    public StatusBarView() {
        this.statusBarPanel = new JPanel();
        this.statusBarTitleLabel = new JLabel();
        this.statusBarMessageLabel = new JLabel();
    }

    //Other methods:
    private void buildStatusBarTitle() {
        this.statusBarTitleLabel.setMinimumSize(new Dimension(270,20));

        this.statusBarTitleLabel.setText("Game information:");
    }

    private void buildStatusBarMessageLabel() {
        this.statusBarMessageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.statusBarMessageLabel.setMinimumSize(new Dimension(270, 30));
        this.statusBarMessageLabel.setText("");
    }

    private void buildStatusBarPanel() {
        this.statusBarPanel.setMinimumSize(new Dimension(270, 50));
        this.statusBarPanel.setLayout(new GridLayout(2, 1, 0, 5));
        this.statusBarPanel.add(statusBarTitleLabel);
        this.statusBarPanel.add(statusBarMessageLabel);
    }

    public JPanel initialize() {
        this.buildStatusBarTitle();
        this.buildStatusBarMessageLabel();
        this.buildStatusBarPanel();
        return this.statusBarPanel;
    }

    public void updateStatusMessage(String statusMessage) {
        this.statusBarMessageLabel.setText(statusMessage);
    }

    public void updateStatusTitle(String statusTitle) {
        this.statusBarTitleLabel.setText(statusTitle);
    }
}
