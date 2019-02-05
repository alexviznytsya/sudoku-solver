/* file: AlgorithmModel.java
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
 *      This class iS kind of storage for all variables that can be used
 *      between different parts of program. From storage of game board and
 *      candidate lists to variables that help interconnection between
 *      different parts of MVC software architectural pattern.
 *
 */
package Model;

import View.BoardCell;
import java.io.File;
import java.util.*;

public class DataModel {

    // Menu properties:
    private File loadFile;
    private File storeFile;
    private boolean checkOnFillMode;
    private boolean updateCandidateListsNeeded;
    private boolean openFileOnLoad = false;

    // Controls properties:
    private BoardCell selectedLabel;


    // Board properties
    private int[][] gameBoardValues;
    private int[] lastBoardUpdate;
    private List<List<Integer>> candidateLists;

    // Default constructor:
    public DataModel() {
        this.loadFile = null;
        this.storeFile = null;
        this.checkOnFillMode = false;
        this.updateCandidateListsNeeded = true;
        this.gameBoardValues = new int[9][9];
        this.lastBoardUpdate = new int[3];
        this.clearGameBoardValues();
        this.candidateLists = new ArrayList<List<Integer>>(81);
        this.initializeCandidateLists();
    }

    // Getter methods;
    public File getLoadFile() {
        return this.loadFile;
    }

    public File getStoreFile() {
        return this.storeFile;
    }

    public boolean isCheckOnFillMode() {
        return this.checkOnFillMode;
    }

    public boolean isUpdateCandidateListsNeeded() {
        return this.updateCandidateListsNeeded;
    }

    public BoardCell getSelectedLabel() {
        return selectedLabel;
    }

    public int[][] getGameBoardValues() {
        return this.gameBoardValues;
    }

    public List<List<Integer>> getCandidateLists() {
        return this.candidateLists;
    }

    public boolean isOpenFileOnLoad() {
        return this.openFileOnLoad;
    }

    public int getGameBoardValueAt(int row, int column) {
        return this.gameBoardValues[row][column];
    }

    public int[] getLastBoardUpdate() {
        return this.lastBoardUpdate;
    }

    // Setter methods:
    public void setOpenFileOnLoad(boolean openFileOnLoad) {
        this.openFileOnLoad = openFileOnLoad;
    }

    public void setLoadFile(File loadFile) {
        this.loadFile = loadFile;
    }

    public void setStoreFile(File storeFile) {
        this.storeFile = storeFile;
    }

    public void setCheckOnFillMode(boolean checkOnFillMode) {
        this.checkOnFillMode = checkOnFillMode;
    }

    public void setUpdateCandidateListsNeeded(boolean updateCandidateListsNeeded) {
        this.updateCandidateListsNeeded = updateCandidateListsNeeded;
    }

    public void setSelectedLabel(BoardCell selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    // Other methos that helps to find, set, and update data:
    public void setSelectedValue(int position) {
        int insertToColumn = position % 9;
        int insertToRow = position / 9;
        this.gameBoardValues[insertToRow][insertToColumn] = this.selectedLabel.getInlineValue();
    }

    public void clearGameBoardValues() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.gameBoardValues[i][j] = 0;
            }
        }
    }

    public void updateValue(int row, int column, int value) {
        this.gameBoardValues[row][column] = value;
        this.lastBoardUpdate[0] = row;
        this.lastBoardUpdate[1] = column;
        this.lastBoardUpdate[2] = value;
    }

    public boolean isCompleted() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(this.gameBoardValues[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void initializeCandidateLists() {
        for(int i = 0; i < 81; i++) {
            List<Integer> tempList = new ArrayList<Integer>(9);
            this.candidateLists.add(tempList);
        }
    }

    public void clearCandidateLists() {
        for(int i = 0; i < 81; i++) {
            this.candidateLists.get(i).clear();
        }
    }

    public int getGameBoardValueAtBox(int box, int position) {
        int counter = 0;
        int row = box - (box % 3);
        int column = 3 * (box % 3);
        for(int i = row; i < (row + 3); i++) {
            for(int j = column; j < (column + 3); j++) {
                if(counter == position) {
                    return this.gameBoardValues[i][j];
                }
                counter++;
            }
        }
        return 0;
    }

    public List<Integer> getCandidateListAt(int row, int column) {
        int position = (row * 9) + column;
        if(gameBoardValues[row][column] > 0) {
            return new ArrayList<Integer>(9);
        } else {
            return this.candidateLists.get(position);
        }
    }

    public List<Integer> getCandidateListAtBox(int box, int position){
        int counter = 0;
        int row = box - (box % 3);
        int column = 3 * (box % 3);
        for(int i = row; i < (row + 3); i++) {
            for(int j = column; j < (column + 3); j++) {
                if(counter == position && this.gameBoardValues[i][j] == 0) {
                    return this.getCandidateListAt(i, j);
                } else {
                    counter++;
                }
            }
        }
        List<Integer> tempList = new ArrayList<Integer>(9);
        return tempList;
    }

    public void setCandidateListAt(List<Integer> candidateSet, int row, int column) {
        int position = (row * 9) + column;
        this.candidateLists.set(position, candidateSet);
    }

    public void setCandidateListAtBox(List<Integer> updatedCandidaeList, int boxIndex, int boxCellPosition) {
        int gameBoardRow = ((boxIndex / 3) * 3) + (boxCellPosition / 3);
        int gameBoardColum = ((boxIndex % 3) * 3) + (boxCellPosition % 3);
        int linearArrayPosition = (gameBoardRow * 9) + gameBoardColum;
        this.candidateLists.set(linearArrayPosition, updatedCandidaeList);
    }
}
