/* file: MenuBarController.java
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
 *      This class is collection of all menu action listeners.
 */
package Controller;

import View.*;
import Model.*;

import javax.swing.JFileChooser;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBarController {

    // Class properties:
    private DataModel dataModel;
    private FileDataModel fileDataModel;
    private AlgorithmsModel algorithmsModel;
    private WindowView windowView;
    private MenuBarView menuBarView;
    private GameBoardController gameBoardController;
    private StatusBarController statusBarController;

    // Default constructor;
    MenuBarController(GameController gameController) {
        this.dataModel = gameController.getDataModel();
        this.fileDataModel = new FileDataModel(dataModel);
        this.algorithmsModel = new AlgorithmsModel(this.dataModel);
        this.menuBarView = new MenuBarView();
        this.windowView = gameController.getWindowController().getWindowView();
        this.gameBoardController = gameController.getGameBoardController();
        this.statusBarController = gameController.getStatusBarController();
    }

    // Other methods:
    class OpenPuzzleMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Sudoku Puzzle", "txt");
            fileChooser.setFileFilter(fileNameExtensionFilter);
            int returnValue = fileChooser.showDialog(windowView, "Open Puzzle");
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                dataModel.clearGameBoardValues();
                dataModel.setLoadFile(fileChooser.getSelectedFile());
                fileDataModel.loadData(algorithmsModel);
                gameBoardController.updateInitialFromDataModel();
                statusBarController.printGameInfoMessage("Puzzle has been loaded from file.");
                algorithmsModel.generateCandidateLists();
            }
        }
    }

    class StorePuzzleMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Sudoku Puzzle", "txt");
            fileChooser.setFileFilter(fileNameExtensionFilter);
            int returnValue = fileChooser.showSaveDialog(windowView);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                dataModel.setStoreFile(fileChooser.getSelectedFile());
                fileDataModel.storeData();
                statusBarController.printGameInfoMessage("Puzzle has been stored to file.");
            }
        }
    }

    class ExitMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    class CheckOnFillMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JCheckBoxMenuItem checkboxMenuItem = (JCheckBoxMenuItem)(event.getSource());
            dataModel.setCheckOnFillMode(checkboxMenuItem.getState());
        }
    }

    class SingleAlgorithmMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(algorithmsModel.singleAlgorithm()) {
                gameBoardController.updateFromDataModel();
                statusBarController.printAlgorithmUpdateMessage("Single", dataModel.getLastBoardUpdate());
                if(algorithmsModel.isSolved()) {
                    JOptionPane.showMessageDialog(windowView, "Congratulation!\nYou have solved this sudoku puzzle.","Congratulation", JOptionPane.PLAIN_MESSAGE);
                    statusBarController.printGameInfoMessage("Congrats! You have won!");
                    if(dataModel.getStoreFile() != null) {
                        fileDataModel.storeData();
                    }
                    return;
                }
            } else {
                statusBarController.printGameInfoMessage("Cannot apply Single Algorithm.");
            }
        }
    }

    class HiddenSingleAlgorithmMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(algorithmsModel.hiddenSingleAlgorithm()) {
                gameBoardController.updateFromDataModel();
                statusBarController.printAlgorithmUpdateMessage("Hiden Single", dataModel.getLastBoardUpdate());
                if(algorithmsModel.isSolved()) {
                    JOptionPane.showMessageDialog(windowView, "Congratulation!\nYou have solved this sudoku puzzle.","Congratulation", JOptionPane.PLAIN_MESSAGE);
                    statusBarController.printGameInfoMessage("Congrats! You have won!");
                    if(dataModel.getStoreFile() != null) {
                        fileDataModel.storeData();
                    }
                    return;
                }
            } else {
                statusBarController.printGameInfoMessage("Cannot apply Hidden Single Algorithm.");
            }
        }
    }

    class LockedCandidateAlgorithmMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(algorithmsModel.lockedCandidate()) {
                statusBarController.updateStatusTitle("Locked Candidate Algorithm:");
                statusBarController.updateStatusMessage("Applied Locked Candidate Algorithm.");
            } else {
                statusBarController.updateStatusTitle("Locked Candidate Algorithm:");
                statusBarController.updateStatusMessage("Cannot apply Locked Candidate Algorithm.");
            }
        }
    }

    class NakedPairsAlgorithmMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
           if(algorithmsModel.nakedPairs()) {
               statusBarController.updateStatusTitle("Naked Pairs Algorithm:");
               statusBarController.updateStatusMessage("Applied Naked Pairs Algorithm.");
           } else {
               statusBarController.updateStatusTitle("Naked Pairs Algorithm:");
               statusBarController.updateStatusMessage("Cannot apply Naked Pairs Algorithm.");
           }
        }
    }

    class FillBlankCellsmMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
           while(true) {
               if(dataModel.isCheckOnFillMode()) {
                   algorithmsModel.generateCandidateLists();
               }
               int[] data = dataModel.getLastBoardUpdate();
               System.out.println(data[0] + " " + data[1] + " " + data[2]);
               if(algorithmsModel.isSolved()) {
                   JOptionPane.showMessageDialog(windowView, "Congratulation!\nYou have solved this sudoku puzzle.","Congratulation", JOptionPane.PLAIN_MESSAGE);
                   statusBarController.printGameInfoMessage("Congrats! You have won!");
                   if(dataModel.getStoreFile() != null) {
                       fileDataModel.storeData();
                   }
                   break;
               } else {
                   if(!algorithmsModel.singleAlgorithm()) {
                       if(!algorithmsModel.hiddenSingleAlgorithm()) {
                           if(!algorithmsModel.lockedCandidate()) {
                               if(!algorithmsModel.nakedPairs()) {
                                   break;
                               }
                           }
                       }
                   }
               }
           }
           gameBoardController.updateFromDataModel();

        }
    }

    class HowToPlayMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(windowView,
                    "The classic Sudoku game involves a grid of 81 squares. The grid is divided into nine blocks,\n" +
                             "each containing nine squares.\n" +
                             "\n" +
                             "The rules of the game are simple: each of the nine blocks has to contain all the numbers 1-9\n" +
                             "within its squares. Each number can only appear once in a row, column or box.\n" +
                             "\n" +
                             "The difficulty lies in that each vertical nine-square column, or horizontal nine-square line\n" +
                             "across, within the larger square, must also contain the numbers 1-9, without repetition or omission.\n" +
                             "\n" +
                             "Every puzzle has just one correct solution. Good luck!", "How To Play Sudoku", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class HowToUseMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(windowView,
                    "1. File Menu:\n" +
                             "   'Load a Puzzle from a File' - This is to allow the player to search the file system for a puzzle stored\n" +
                             "    in a text file.\n\n" +
                             "   'Store a Puzzle Into a File' – This is to allow the player to store the current puzzle into a text file\n" +
                             "    named by the player.\n\n" +
                             "   'Exit' - Exit the program.\n\n" +
                             "2. Hints Menu:\n" +
                             "   'Check on Fill' - When this mode is on, if the user attempts to place a digit into a cell that is NOT in\n" +
                             "   the cells current candidate list, an error message will be given and the digit is not placed into the cell.\n" +
                             "   'Single Algorithm' - Fill a single blank cell using Single Algorithm.\n\n" +
                             "   'Hidden Single Algorithm' - Fill a single blank cell using Hidden Single Algorithm.\n\n" +
                             "   'Locked Candidate Algorithm' - Find and reduce amount of candidates in first available blank cell using\n" +
                             "    Locked Candidate Algorithm. After using this algorithm it is up to player wich algorithm is to be used\n" +
                             "    to resolve the value in first available cell.\n\n" +
                             "    'Naked Pairs Algorithm' - Find and reduce amount of candidates in first available blank cell using\n" +
                             "    Naked Pairs Algorithm. After using this algorithm it is up to player wich algorithm is to be used\n" +
                             "    to resolve the value in first available cell.\n\n" +
                             "    'Fill Blank Cells' - Fill as much as possible black cells using all four algorithms above.\n\n" +
                             "3. HelpMenu:\n" +
                             "    'How To Play Sudoku' - Short manual how to play sudoku game.\n\n" +
                             "    'How To Use Program' - This window :)\n\n" +
                             "    'About' - Information about students who wrote this program.\n\n" +
                             "Command Line Arguments:\n" +
                             "     'java SudokuSolver puzzleData.txt' - Where puzzleData.txt should be replaced by the puzzle text file\n" +
                             "     that will be loaded automaticly when game starts.\n\n" +
                             "     'java SudokuSolver -o puzzleData.txt' - If parameter -o is being used then program will store final\n" +
                             "     result of the puzzle to the puzzleData.txt text file."+
                             "", "How To Use Program", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class AboutMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(windowView,
                    "Sudoku Solver\n" +
                            "\n" +
                            "Alex Viznytsya\n" +
                            "10/26/2017",
                    "About", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void initialize() {
        this.windowView.setJMenuBar(this.menuBarView.buildMenuBar());
        this.menuBarView.addMenuItemListener("loadPuzzle", new OpenPuzzleMenuListener());
        this.menuBarView.addMenuItemListener("storePuzzle", new StorePuzzleMenuListener());
        this.menuBarView.addMenuItemListener("exit", new ExitMenuListener());
        this.menuBarView.addCheckBoxMenuItemListener("checkOnFill", new CheckOnFillMenuListener());
        this.menuBarView.addMenuItemListener("singleAlgorithm", new SingleAlgorithmMenuListener());
        this.menuBarView.addMenuItemListener("hiddenSingleAlgorithm", new HiddenSingleAlgorithmMenuListener());
        this.menuBarView.addMenuItemListener("lockedCandidateAlgorithm", new LockedCandidateAlgorithmMenuListener());
        this.menuBarView.addMenuItemListener("nakedPairsAlgorithm", new NakedPairsAlgorithmMenuListener());
        this.menuBarView.addMenuItemListener("fillBlankCells", new FillBlankCellsmMenuListener());
        this.menuBarView.addMenuItemListener("howToPlay", new HowToPlayMenuListener());
        this.menuBarView.addMenuItemListener("howToUse", new HowToUseMenuListener());
        this.menuBarView.addMenuItemListener("about", new AboutMenuListener());
    }
}
