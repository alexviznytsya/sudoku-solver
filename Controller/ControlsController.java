/* file: ControlsController.java
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
 *      This class initialize all controls that required for game:
 *          Clicks on game boards, selection of number buttons.
 *
 */
package Controller;

import View.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ControlsController {

    // Class properties:
    private WindowView windowView;
    private ControlsView controlsView;
    private DataModel dataModel;

    // Default constructor
    ControlsController(GameController gameController) {
        this.windowView = gameController.getWindowController().getWindowView();
        this.dataModel = gameController.getDataModel();
        this.controlsView = new ControlsView();
    }


    // Other methods:
    public void initialize() {
        this.windowView.getMainContentPanel().add("GameControls", this.controlsView.initialize());
        this.controlsView.addNumberControlMouseListener(new NumberControlMouseListener());
        this.controlsView.addDeleteMouseListener(new DeleteControlMouseListener());
        this.controlsView.addHelpMouseListener(new HelpControlMouseListener());

    }

    // Mouse Listener classes:
    class NumberControlMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }
        @Override
        public void mousePressed(MouseEvent e) {
            controlsView.setDefaultState();
            BoardCell cell = (BoardCell)e.getSource();
            cell.setBorder(BorderFactory.createLoweredBevelBorder());
            cell.setBackground(new Color(204, 229, 255));
            cell.setSelected(true);
            dataModel.setSelectedLabel(cell);
        }
        @Override
        public void mouseReleased(MouseEvent e) {

        }
        @Override
        public void mouseEntered(MouseEvent e) {
            BoardCell cell = (BoardCell)e.getSource();
            cell.setBackground(new Color(204, 229, 255));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            BoardCell cell = (BoardCell)e.getSource();
            if(!cell.isSelected()) {
                cell.setBackground(Color.WHITE);
            }
        }
    }

    class DeleteControlMouseListener extends NumberControlMouseListener {

    }

    class HelpControlMouseListener extends NumberControlMouseListener {

    }


}
