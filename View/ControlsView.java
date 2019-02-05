/* file: ControlsView.java
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
 *     This class reads gameboards values from file and saves
 *     game boards values to file. IO Model.
 *
 */
package View;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ControlsView {

    // Class properties:
    private JPanel mainControlsPanel;
    private JPanel numberControlPanel;
    private List<BoardCell> numberControlList;
    private BoardCell helpControl;
    private BoardCell deleteControl;

    // Default constructor:
    public ControlsView() {
        this.mainControlsPanel = new JPanel();
        this.numberControlPanel = new JPanel();
        this.numberControlList = new ArrayList<BoardCell>(9);
        this.helpControl = null;
        this.deleteControl = null;
    }

    // Other methods used to initalize all game visual controll elements:
    private void buidDeleteControl() {
        this.deleteControl = new BoardCell();
        this.deleteControl.initialize();
        this.deleteControl.setText("X");
        this.deleteControl.setInlineValue(0);
        this.deleteControl.setBorder(BorderFactory.createRaisedBevelBorder());
        this.mainControlsPanel.add(this.deleteControl);
    }

    private void buidHelpControl() {
        this.helpControl = new BoardCell();
        this.helpControl.initialize();
        this.helpControl.setText("?");
        this.helpControl.setInlineValue(10);
        this.helpControl.setBorder(BorderFactory.createRaisedBevelBorder());
        this.mainControlsPanel.add(this.helpControl);
    }

    private void buildNumberControls() {
        this.numberControlPanel.setMinimumSize(new Dimension(30, 270));
        for(int i = 1; i <= 9; i++) {
            BoardCell tempCell = new BoardCell();
            tempCell.setBorder(BorderFactory.createRaisedBevelBorder());
            tempCell.initialize();
            tempCell.setText(Integer.toString(i));
            tempCell.setInlineValue(i);
            this.numberControlList.add(tempCell);
            this.mainControlsPanel.add(tempCell);
        }
    }

    private void buildMainControlsPanel() {
        this.mainControlsPanel.setLayout(new GridLayout(11,1));
    }

    public void addNumberControlMouseListener(MouseListener mouseListener) {
        for(int i = 0; i < 9; i++) {
            this.numberControlList.get(i).addMouseListener(mouseListener);
        }
    }

    public void addDeleteMouseListener(MouseListener mouseListener) {
        this.deleteControl.addMouseListener(mouseListener);
    }

    public void addHelpMouseListener(MouseListener mouseListener) {
        this.helpControl.addMouseListener(mouseListener);
    }

    private void setDefaultCellState(BoardCell cell) {
        cell.setBorder(BorderFactory.createRaisedBevelBorder());
        cell.setSelected(false);
        cell.setBackground(Color.WHITE);
    }

    public void setDefaultState() {
        for(int i = 0; i < 9; i++) {
            this.setDefaultCellState(this.numberControlList.get(i));
        }
        this.setDefaultCellState(this.helpControl);
        this.setDefaultCellState(this.deleteControl);
    }

    public JPanel initialize() {
        this.buildMainControlsPanel();
        this.buildNumberControls();
        this.buidHelpControl();
        this.buidDeleteControl();
        return this.mainControlsPanel;
    }
}
