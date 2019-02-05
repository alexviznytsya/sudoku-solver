package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


public class GameBoardController {


    private DataModel dataModel;
    private FileDataModel fileDataModel;
    private AlgorithmsModel algorithmsModel;
    private WindowView windowView;
    private StatusBarController statusBarController;
    private GameBoardView gameBoardView;


    GameBoardController(GameController gameController) {
        this.dataModel = gameController.getDataModel();
        this.fileDataModel = new FileDataModel(this.dataModel);
        this.algorithmsModel = new AlgorithmsModel(dataModel);
        this.windowView = gameController.getWindowController().getWindowView();
        this.statusBarController = gameController.getStatusBarController();
        this.gameBoardView = new GameBoardView();

    }


    public void initialize() {
        if(this.dataModel.isOpenFileOnLoad()) {
            this.fileDataModel.loadData(algorithmsModel);
            this.statusBarController.updateStatusMessage("Puzzle has been loadded.");
        } else {
            this.statusBarController.updateStatusMessage("Please load puzzle from File Menu.");
        }
        this.windowView.getMainContentPanel().add("GameBoard",this.gameBoardView.initialize());
        this.gameBoardView.addCellMouseListener(new CellsMouseListener());
        this.updateInitialFromDataModel();
    }

    class CellsMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            BoardCell cell = (BoardCell)e.getSource();
            int row = cell.getInlineValue() / 9;
            int column = cell.getInlineValue() % 9;
            if(dataModel.isUpdateCandidateListsNeeded()) {
                algorithmsModel.generateCandidateLists();
            }

            List<Integer> candidateList = dataModel.getCandidateListAt(row, column);
            row += 1;
            column += 1;
            statusBarController.printGameInfoMessage("");
            try {
                if(dataModel.getSelectedLabel().getInlineValue() == 10) {
                    if(cell.isActive()) {
                        statusBarController.updateStatusTitle("Candidate List:");
                        if(candidateList.isEmpty()) {
                            statusBarController.updateStatusMessage("None.");
                        } else {
                            statusBarController.updateStatusMessage(candidateList.toString());
                        }
                    } else {
                        System.out.println("Not allowed change this cell");
                    }
                } else if (dataModel.getSelectedLabel().getInlineValue() == 0){
                    if (cell.isActive()) {
                        cell.setText("");
                        if(dataModel.getGameBoardValueAt(row - 1, column - 1) == 0) {
                            statusBarController.printGameInfoMessage("Cell at [" + row + ", " + column + "] is empty.");
                        } else {
                            statusBarController.printGameInfoMessage("Cell at [" + row + ", " + column + "] has been deleted.");
                            dataModel.setSelectedValue(cell.getInlineValue());
                        }
                    } else {
                        statusBarController.printErrorMessage("Cannot delete loaded cell at [" + row + ", " + column + "].");
                        System.out.println("Not allowed change this cell");
                    }
                } else {
                    if (cell.isActive()) {
                        if(dataModel.isCheckOnFillMode()) {
                            if(candidateList.contains(dataModel.getSelectedLabel().getInlineValue())) {
                                cell.setText(Integer.toString(dataModel.getSelectedLabel().getInlineValue()));
                                dataModel.setSelectedValue(cell.getInlineValue());
                                statusBarController.printGameInfoMessage("Inserted value " +
                                        dataModel.getSelectedLabel().getInlineValue() +
                                        " at [" + row + ", " + column + "] position.");
                            } else {
                                statusBarController.printErrorMessage("Value " + dataModel.getSelectedLabel().getInlineValue() +" is not in candidate list.");
                                System.err.println("Value is not in candidate list.");
                            }
                        } else {
                            cell.setText(Integer.toString(dataModel.getSelectedLabel().getInlineValue()));
                            dataModel.setSelectedValue(cell.getInlineValue());
                            statusBarController.printGameInfoMessage("Inserted value " +
                                    dataModel.getSelectedLabel().getInlineValue() +
                                    " at [" + row + ", " + column + "] position.");
                            dataModel.setUpdateCandidateListsNeeded(true);
                        }
                        if(algorithmsModel.isSolved()) {
                            updateFromDataModel();
                            JOptionPane.showMessageDialog(windowView, "Congratulation!\nYou have solved this sudoku puzzle.","Congratulation", JOptionPane.PLAIN_MESSAGE);
                            if(dataModel.getStoreFile() != null) {
                                fileDataModel.storeData();
                            }
                            System.out.println("Congrats it is solved");
                        }
                    } else {
                        statusBarController.printGameInfoMessage("Cannot change loaded cell at [" + row + ", " + column + "].");
                        System.out.println("Not allowed change this cell");
                    }
                }
            } catch (NullPointerException npe){
                statusBarController.printGameInfoMessage("Please select number to insert.");
            }
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
            cell.setBackground(Color.white);
        }
    }

    public void updateInitialFromDataModel() {
        this.gameBoardView.updateInitialGameBoard(dataModel.getGameBoardValues());
    }

    public void updateFromDataModel() {
        this.gameBoardView.updateGameBoard(dataModel.getGameBoardValues());
    }

    public boolean isSolved() {
        return false;
    }

}
