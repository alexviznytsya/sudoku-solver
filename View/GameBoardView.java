/* file: GameBoardView.java
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
 *     This class is visual representation of game board. It uses provided
 *     data to create and update game board.
 *
 */
package View;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameBoardView {

    // Class properties:
    private JPanel gameBoardPanel;
    List<BoardCell> boardCellList;
    private int cellCounter;
    private int[] cellIndexMap;


    // Default Constructor:
    public GameBoardView() {
        this.gameBoardPanel = new JPanel();
        this.boardCellList = new ArrayList<BoardCell>(81);
        for (int i =0; i < 81; i++) {
            this.boardCellList.add(null);
        }
        this.cellCounter = 0;
        this.cellIndexMap = new int[] {
                0,  1,  2,  9,  10, 11, 18, 19, 20,
                3,  4,  5,  12, 13, 14, 21, 22, 23,
                6,  7,  8,  15, 16, 17, 24, 25, 26,
                27, 28, 29, 36, 37, 38, 45, 46, 47,
                30, 31, 32, 39, 40, 41, 48, 49, 50,
                33, 34, 35, 42, 43, 44, 51, 52, 53,
                54, 55, 56, 63, 64, 65, 72, 73, 74,
                57, 58, 59, 66, 67, 68, 75, 76, 77,
                60, 61, 62, 69, 70, 71, 78, 79, 80
        };

    }

    // Getter methods:
    public List<BoardCell> getBoardCellList() {
        return this.boardCellList;
    }


    // Other methods:
    private Border addInnerBoxBorder(int cellNum, Color borderColor) {
        if(cellNum == 1 || cellNum == 4 || cellNum == 2 || cellNum == 5 ) {
            return BorderFactory.createMatteBorder(0,0,1,1, borderColor);
        } else if (cellNum == 3 || cellNum == 6) {
            return BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor);
        } else if (cellNum == 7 || cellNum == 8) {
            return BorderFactory.createMatteBorder(0,0,0,1, borderColor);
        } else if (cellNum == 9) {
            return BorderFactory.createMatteBorder(0,0,0,0, borderColor);
        } else {
            return BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor);
        }
    }

    private Border addSquareBorder(int cellNum, Color borderColor) {
        if(cellNum == 1 || cellNum == 2 || cellNum == 4 ||cellNum == 5) {
            return BorderFactory.createMatteBorder(2,2,0,0, borderColor);
        } else if (cellNum == 3 || cellNum == 6 ) {
            return BorderFactory.createMatteBorder(2, 2, 0, 2, borderColor);
        } else if (cellNum == 7 || cellNum == 8) {
            return BorderFactory.createMatteBorder(2,2,2,0, borderColor);
        } else if (cellNum == 9) {
            return BorderFactory.createMatteBorder(2,2,2,2, borderColor);
        } else {
            return BorderFactory.createMatteBorder(2, 2, 2, 2, borderColor);
        }
    }

    private JPanel build3x3BoardCells() {
        JPanel gameSquare = new JPanel();
        gameSquare.setLayout(new GridLayout(3,3));
        for (int i = 1; i <= 9; i++) {
            BoardCell tempCell = new BoardCell();
            tempCell.initialize();
            tempCell.setBorder(this.addInnerBoxBorder(i, Color.lightGray));
            tempCell.setInlineValue(this.cellIndexMap[cellCounter]);
            tempCell.setBoxedValue(cellCounter);
            gameSquare.add(tempCell);
            this.boardCellList.set(this.cellIndexMap[cellCounter], tempCell);
            this.cellCounter++;
        }
        return gameSquare;
    }

    private void build3x3BoardSquares() {
        this.gameBoardPanel.setLayout(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JPanel tempSquare = this.build3x3BoardCells();
            tempSquare.setBorder(this.addSquareBorder(i, Color.black));
            this.gameBoardPanel.add(tempSquare);
        }
    }

    public void addCellMouseListener(MouseListener mouseListener) {
        for (int i = 0; i < 81; i++) {
            this.boardCellList.get(i).addMouseListener(mouseListener);
        }
    }

    public JPanel initialize() {
        this.gameBoardPanel.setMinimumSize(new Dimension(270, 270));
        this.build3x3BoardSquares();
        return this.gameBoardPanel;
    }

    private void setAllCellsAsActive() {
        for(int i = 0; i < 81; i++) {
            this.boardCellList.get(i).setActive(true);
        }
    }

    public void updateInitialGameBoard(int[][] gameBoard) {
        this.setAllCellsAsActive();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(gameBoard[i][j] != 0) {
                    this.boardCellList.get((i * 9) + j).setActive(false);
                    this.boardCellList.get((i * 9) + j).setText(Integer.toString(gameBoard[i][j]));
                } else {
                    this.boardCellList.get((i * 9) + j).setText("");
                }
            }
        }
    }

    public void updateGameBoard(int[][] gameBoard) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(gameBoard[i][j] != 0) {
                    this.boardCellList.get((i * 9) + j).setText(Integer.toString(gameBoard[i][j]));
                } else {
                    this.boardCellList.get((i * 9) + j).setText("");
                }
            }
        }
    }

}