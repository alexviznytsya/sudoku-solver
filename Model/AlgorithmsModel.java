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
 *      This class is the brain of the entire game. It has function and helper functions
 *      for for game algorithms algorithms:
 *          Singles
 *          Hidden Singles (sometimes called Hidden Values)
 *          Locked Candidates
 *          Naked Pairs
 *
 */
package Model;

import java.util.*;

public class AlgorithmsModel {

    private DataModel dataModel;


    public AlgorithmsModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    private int getBoxValue(int row, int column) {
        return ((row / 3) * 3 + (column / 3));
    }

    private void getRowSet(Set<Integer> candidateSet, int row) {
        for(int i = 0; i < 9; i++) {
            if(dataModel.getGameBoardValueAt(row, i) != 0) {
                candidateSet.add(dataModel.getGameBoardValueAt(row, i));
            }
        }
    }

    private void getColumnSet(Set<Integer> candidateSet, int column) {
        for(int i = 0; i < 9; i++) {
            if(dataModel.getGameBoardValueAt(i, column) != 0) {
                candidateSet.add(dataModel.getGameBoardValueAt(i, column));
            }
        }
    }

    private void getBoxedSet(Set<Integer> candidateSet, int box) {
        for(int i = 0; i < 9; i++) {
            if(dataModel.getGameBoardValueAtBox(box, i) != 0) {
                candidateSet.add(dataModel.getGameBoardValueAtBox(box, i));
            }
        }
    }

    private Set<Integer> getSetValues(int row, int column, int box) {
        Set<Integer> candidateSet = new TreeSet<Integer>();
        this.getRowSet(candidateSet, row);
        this.getColumnSet(candidateSet, column);
        this.getBoxedSet(candidateSet, box);
        return candidateSet;
    }

    private List<Integer> getListCompliment(Set<Integer> candidateSet) {
        List<Integer> tempCandidateSet = new ArrayList<Integer>();
        for(int i = 1; i <= 9; i++) {
            if(!candidateSet.contains(i)) {
                tempCandidateSet.add(i);
            }
        }
        return tempCandidateSet;
    }

    private Set<Integer> combineTwoSets (Set<Integer> setOne, Set<Integer> setTwo) {
        setOne.addAll(setTwo);
        return setOne;

    }


    public List<Integer> getCandidateList(int row, int column) {
        return this.getListCompliment(this.getSetValues(row, column, this.getBoxValue(row, column)));
    }

    public void generateCandidateLists() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(this.dataModel.getGameBoardValueAt(i, j) == 0) {
                    this.dataModel.setCandidateListAt(this.getCandidateList(i,j), i, j);
                } else {
                    this.dataModel.setCandidateListAt(new ArrayList<Integer>(9), i, j);
                }

            }
        }
    }

    public boolean isSolved() {
        if(this.dataModel.isCompleted()) {
            for(int i = 0; i < 9; i++) {
                Set<Integer> compRowSet = new TreeSet<Integer>();
                Set<Integer> compColumnSet = new TreeSet<Integer>();
                this.getRowSet(compRowSet, i);
                this.getColumnSet(compColumnSet, i);
                if(compRowSet.size() < 9 || compColumnSet.size() < 9) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean singleAlgorithm() {
        if(this.dataModel.isUpdateCandidateListsNeeded()) {
            this.generateCandidateLists();
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(this.dataModel.getGameBoardValueAt(i, j) == 0) {
                    if(this.dataModel.getCandidateListAt(i, j).size() == 1) {
                        this.dataModel.updateValue(i, j, this.dataModel.getCandidateListAt(i, j).get(0));
                        this.dataModel.setCandidateListAt(new ArrayList<Integer>(9), i, j);
                        this.dataModel.setUpdateCandidateListsNeeded(true);
                        System.out.println("Single Algorithm: found cell: [" + (i + 1) +", " + (j + 1)+ "] with value: " + this.dataModel.getGameBoardValueAt(i, j) + " to resolve.");
                        return true;
                    }
                }
            }
        }
        this.dataModel.setUpdateCandidateListsNeeded(false);
        return false;
    }

    private Set<Integer> convertListToSet(List<Integer> list) {
        Set<Integer> tempSet = new TreeSet<Integer>(list);
        return tempSet;
    }

    public boolean hiddenSingleAlgorithm() {
        if(this.dataModel.isUpdateCandidateListsNeeded()) {
            this.generateCandidateLists();
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(this.dataModel.getGameBoardValueAt(i, j) == 0) {
                    List<Integer> currentCandidateList = this.dataModel.getCandidateListAt(i, j);
                    int positionInBox = (((i % 3) * 3) + (j % 3));
                    Set<Integer> boxSet = new TreeSet<Integer>();
                    for(int k = 0; k < 9; k++) {
                        List<Integer> boxListAtIndex = this.dataModel.getCandidateListAtBox(this.getBoxValue(i, j), k);
                        if(boxListAtIndex != null && positionInBox != k) {
                            boxSet = this.combineTwoSets(boxSet, this.convertListToSet(boxListAtIndex));
                        }
                    }
                    List<Integer> finalCandidateList = new ArrayList<Integer>(9);
                    for(int k = 0; k < currentCandidateList.size(); k++) {
                        if (!boxSet.contains(currentCandidateList.get(k))) {
                            finalCandidateList.add(currentCandidateList.get(k));
                        }
                    }
                    if(finalCandidateList.size() == 1) {
                        this.dataModel.updateValue(i, j, finalCandidateList.get(0));
                        this.dataModel.setCandidateListAt(new ArrayList<Integer>(9), i, j);
                        this.dataModel.setUpdateCandidateListsNeeded(true);
                        System.out.println("Hidden Single Algorithm: found cell: [" + (i + 1) +", " + (j + 1)+ "] with value: " + finalCandidateList.get(0) + " to resolve.");
                        return true;
                    }
                }
            }
        }
        this.dataModel.setUpdateCandidateListsNeeded(false);
        return false;
    }

    private int countCandidatesInRow(int candidate, int rowIndex) {
        int candidateCounter = 0;
        for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                candidateCounter += 1;
            }
        }
        return candidateCounter;
    }

    private int countCandidatesInColumn(int candidate, int columnIndex) {
        int candidateCounter = 0;
        for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                candidateCounter += 1;
            }
        }
        return candidateCounter;
    }

    private int countCandidatesInBox(int candidate, int boxIndex) {
        int candidateCounter = 0;
        for(int BoxCellIndex = 0; BoxCellIndex < 9; BoxCellIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, BoxCellIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                candidateCounter += 1;
            }
        }
        return candidateCounter;
    }

    private int countCandidatesInBoxRow(int candidate, int boxIndex, int boxRow) {
        int candidateCounter = 0;
        for(int boxCellIndex = (boxRow * 3); boxCellIndex < ((boxRow * 3) + 3); boxCellIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                candidateCounter += 1;
            }
        }
        return candidateCounter;
    }

    private int countCandidatesInBoxColumn(int candidate, int boxIndex, int boxColumn) {
        int candidateCounter = 0;
        for(int boxCellIndex = boxColumn; boxCellIndex < 9; boxCellIndex += 3) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                candidateCounter += 1;
            }
        }
        return candidateCounter;
    }

    private boolean deleteCandidateFromRow(int candidate, int rowIndex, int boxIndex) {
        boolean deletedCandidates = false;
        for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
            int currentBoxIndex = this.getBoxValue(rowIndex, columnIndex);
            if(currentBoxIndex == boxIndex) {
                continue;
            }
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                tempCandidateList.remove(tempCandidateList.indexOf(candidate));
                this.dataModel.setCandidateListAt(tempCandidateList, rowIndex, columnIndex);
                deletedCandidates = true;
            }
        }
        return deletedCandidates;
    }

    private boolean deleteCandidateFromColumn(int candidate, int columnIndex, int boxIndex) {
        boolean deletedCandidates = false;
        for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
            int currentBoxIndex = this.getBoxValue(rowIndex, columnIndex);
            if(currentBoxIndex == boxIndex) {
                continue;
            }
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                tempCandidateList.remove(tempCandidateList.indexOf(candidate));
                this.dataModel.setCandidateListAt(tempCandidateList, rowIndex, columnIndex);
                deletedCandidates = true;
            }
        }
        return deletedCandidates;
    }


    public boolean deleteCandidateFromBoxRows(int candidate, int boxRow, int boxIndex) {
        boolean deletedCandidates = false;
        for(int boxCellIndex = 0; boxCellIndex < 9; boxCellIndex++) {
            int currentBoxRow = boxCellIndex / 3;
            if(currentBoxRow == boxRow) {
                continue;
            }
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                tempCandidateList.remove(tempCandidateList.indexOf(candidate));
                this.dataModel.setCandidateListAtBox(tempCandidateList, boxIndex, boxCellIndex);
                deletedCandidates = true;
            }
        }
        return deletedCandidates;
    }

    public boolean deleteCandidateFromBoxColumns(int candidate, int boxColumn, int boxIndex) {
        boolean deletedCandidates = false;
        for(int boxCellIndex = 0; boxCellIndex < 9; boxCellIndex++) {
            int currentBoxColumn = boxCellIndex % 3;
            if(currentBoxColumn == boxColumn) {
                continue;
            }
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(tempCandidateList.size() != 0 && tempCandidateList.contains(candidate)) {
                tempCandidateList.remove(tempCandidateList.indexOf(candidate));
                this.dataModel.setCandidateListAtBox(tempCandidateList, boxIndex, boxCellIndex);
                deletedCandidates = true;
            }
        }
        return deletedCandidates;
    }

    public boolean lockedCandidate() {

        if(this.dataModel.isUpdateCandidateListsNeeded()) {
            this.generateCandidateLists();
        }

        // Check box to row and box to column:
        for(int boxIndex = 0; boxIndex < 9; boxIndex++) {
            for(int boxCellIndex = 0; boxCellIndex < 9; boxCellIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
                int curentGameBoardRow = ((boxIndex / 3) * 3) + (boxCellIndex / 3);
                int currentGameBoardColumn = ((boxIndex % 3) * 3) + (boxCellIndex % 3);
                int currentBoxRow = boxIndex / 3;
                int currentBoxColumn = boxCellIndex / 3;
                if(cellCandidateList.size() == 0) {
                    continue;
                }
                for(int cellCandidateIndex = 0; cellCandidateIndex < cellCandidateList.size(); cellCandidateIndex++) {
                    int cellCandidateValue = cellCandidateList.get(cellCandidateIndex);
                    int candidateCountInBox = this.countCandidatesInBox(cellCandidateValue, boxIndex);
                    int candidateCountInBoxRow = this.countCandidatesInBoxRow(cellCandidateValue, boxIndex, currentBoxRow);
                    int candidateCountInColumn = this.countCandidatesInBoxColumn(cellCandidateValue, boxIndex, currentBoxColumn);
                    if(candidateCountInBox == candidateCountInBoxRow) {
                        if(!this.deleteCandidateFromRow(cellCandidateValue, curentGameBoardRow, boxIndex)) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            System.out.println("Locked Candidate: Reduced box to row: with value: " + cellCandidateValue + ", box index: " + (boxIndex + 1) + ", row: " + (curentGameBoardRow + 1));
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            return true;
                        }
                    }
                    if(candidateCountInBox == candidateCountInColumn) {
                        if(!this.deleteCandidateFromColumn(cellCandidateValue, currentGameBoardColumn, boxIndex)) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            System.out.println("Locked Candidate: Reduced box to column: with value: " + cellCandidateValue + ", box index: " + (boxIndex + 1) + ", column: " + (currentGameBoardColumn + 1));
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            return true;
                        }
                    }
                }
            }
        }

        // check row to box
        for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
            for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
                int[] boxIndex = new int[3];
                boxIndex[0] = this.getBoxValue(rowIndex, 0);
                boxIndex[1] = this.getBoxValue(rowIndex, 3);
                boxIndex[2] = this.getBoxValue(rowIndex, 6);
                int currentBoxRow = rowIndex % 3;
                if(cellCandidateList.size() == 0) {
                    continue;
                }
                for(int cellCandidateIndex = 0; cellCandidateIndex < cellCandidateList.size(); cellCandidateIndex++) {
                    int cellCandidateValue = cellCandidateList.get(cellCandidateIndex);
                    int candidateCountInRow = this.countCandidatesInRow(cellCandidateValue, rowIndex);
                    int[] candidateCountInBoxRow = new int[3];
                    candidateCountInBoxRow[0] = this.countCandidatesInBoxRow(cellCandidateValue, boxIndex[0], currentBoxRow);
                    candidateCountInBoxRow[1] = this.countCandidatesInBoxRow(cellCandidateValue, boxIndex[1], currentBoxRow);
                    candidateCountInBoxRow[2] = this.countCandidatesInBoxRow(cellCandidateValue, boxIndex[2], currentBoxRow);
                    if(candidateCountInRow == candidateCountInBoxRow[0]) {
                        if(!this.deleteCandidateFromBoxRows(cellCandidateValue, currentBoxRow, boxIndex[0])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced row to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[0] + 1) + ", row: " + (currentBoxRow + 1));
                            return true;
                        }
                    } else if(candidateCountInRow == candidateCountInBoxRow[1]) {
                        if(!this.deleteCandidateFromBoxRows(cellCandidateValue, currentBoxRow, boxIndex[1])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced row to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[1] + 1) + ", row: " + (currentBoxRow + 1));
                            return true;
                        }
                    } else if(candidateCountInRow == candidateCountInBoxRow[2]) {
                        if(!this.deleteCandidateFromBoxRows(cellCandidateValue, currentBoxRow, boxIndex[2])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced row to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[2] + 1) + ", row: " + (currentBoxRow + 1));
                            return true;
                        }
                    }
                }
            }
        }

        // check column to box
        for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
            for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
                int[] boxIndex = new int[3];
                boxIndex[0] = this.getBoxValue(0, columnIndex);
                boxIndex[1] = this.getBoxValue(3, columnIndex);
                boxIndex[2] = this.getBoxValue(6, columnIndex);
                int currentBoxColumn = columnIndex % 3;
                if(cellCandidateList.size() == 0) {
                    continue;
                }
                for(int cellCandidateIndex = 0; cellCandidateIndex < cellCandidateList.size(); cellCandidateIndex++) {
                    int cellCandidateValue = cellCandidateList.get(cellCandidateIndex);
                    int candidateCountInColumn = this.countCandidatesInColumn(cellCandidateValue, rowIndex);
                    int[] candidateCountInBoxColumn = new int[3];
                    candidateCountInBoxColumn[0] = this.countCandidatesInBoxColumn(cellCandidateValue, boxIndex[0], currentBoxColumn);
                    candidateCountInBoxColumn[1] = this.countCandidatesInBoxColumn(cellCandidateValue, boxIndex[1], currentBoxColumn);
                    candidateCountInBoxColumn[2] = this.countCandidatesInBoxColumn(cellCandidateValue, boxIndex[2], currentBoxColumn);
                    if(candidateCountInColumn == candidateCountInBoxColumn[0]) {
                        if(!this.deleteCandidateFromBoxColumns(cellCandidateValue, currentBoxColumn, boxIndex[0])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced column to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[0] + 1) + ", column: " + (currentBoxColumn + 1));
                            return true;
                        }
                    } else if(candidateCountInColumn == candidateCountInBoxColumn[1]) {
                        if(!this.deleteCandidateFromBoxColumns(cellCandidateValue, currentBoxColumn, boxIndex[1])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced column to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[1] + 1) + ", column: " + (currentBoxColumn + 1));
                            return true;
                        }
                    } else if(candidateCountInColumn == candidateCountInBoxColumn[2]) {
                        if(!this.deleteCandidateFromBoxColumns(cellCandidateValue, currentBoxColumn, boxIndex[2])) {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                        } else {
                            this.dataModel.setUpdateCandidateListsNeeded(false);
                            System.out.println("Locked Candidate: Reduced column to box: with value: " + cellCandidateValue + ", box index: " + (boxIndex[2] + 1) + ", column: " + (currentBoxColumn + 1));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int findAnotherPairInRow(List<Integer> seachedCandidateList, int startIndex, int rowIndex) {
        for(int columnIndex = startIndex; columnIndex < 9; columnIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() == 0 || tempCandidateList.size() != 2) {
                continue;
            }
            if(seachedCandidateList.containsAll(tempCandidateList)) {
                return columnIndex;
            }
        }
        return 0;
    }

    private int findAnotherPairInColumn(List<Integer> seachedCandidateList, int startIndex, int columnIndex) {
        for(int rowIndex = startIndex; rowIndex < 9; rowIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(tempCandidateList.size() == 0 || tempCandidateList.size() != 2) {
                continue;
            }
            if(seachedCandidateList.containsAll(tempCandidateList)) {
                return rowIndex;
            }
        }
        return 0;
    }

    private int findAnotherPairInBox(List<Integer> seachedCandidateList, int startIndex, int boxIndex) {
        for(int boxCellIndex = startIndex; boxCellIndex < 9; boxCellIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(tempCandidateList.size() == 0 || tempCandidateList.size() != 2) {
                continue;
            }
            if(seachedCandidateList.containsAll(tempCandidateList)) {
                return boxCellIndex;
            }
        }
        return 0;
    }

    private boolean deleteNakedPairsFromRow(List<Integer> cellCandidateList, List<Integer> nakedPairsList, int rowIndex) {
        boolean foundPairToDelete = false;
        for(int columnIndex = 0; columnIndex < 9; columnIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if (nakedPairsList.contains(columnIndex) || !tempCandidateList.containsAll(cellCandidateList)) {
                continue;
            } else {
                tempCandidateList.removeAll(cellCandidateList);
                this.dataModel.setCandidateListAt(tempCandidateList, rowIndex, columnIndex);
                foundPairToDelete = true;
            }
        }
        return foundPairToDelete;
    }

    private boolean deleteNakedPairsFromColumn(List<Integer> cellCandidateList, List<Integer> nakedPairsList, int columnIndex) {
        boolean foundPairToDelete = false;
        for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
            if(nakedPairsList.contains(rowIndex) || !tempCandidateList.containsAll(cellCandidateList)) {
                continue;
            } else {
                tempCandidateList.removeAll(cellCandidateList);
                this.dataModel.setCandidateListAt(tempCandidateList, rowIndex, columnIndex);
                foundPairToDelete = true;
            }
        }
        return foundPairToDelete;
    }

    private boolean deleteNakedPairsFromBox(List<Integer> cellCandidateList, List<Integer> nakedPairsList, int boxIndex) {
        boolean foundPairToDelete = false;
        for(int boxCellIndex = 0; boxCellIndex < 9; boxCellIndex++) {
            List<Integer> tempCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
            if(nakedPairsList.contains(boxCellIndex) || !tempCandidateList.containsAll(cellCandidateList)) {
                continue;
            } else {
                tempCandidateList.removeAll(cellCandidateList);
                this.dataModel.setCandidateListAtBox(tempCandidateList, boxIndex, boxCellIndex);
                foundPairToDelete = true;
            }
        }
        return foundPairToDelete;
    }

    public boolean nakedPairs() {
        if(this.dataModel.isUpdateCandidateListsNeeded()) {
            this.generateCandidateLists();
        }
        // check rows:
        for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
            for(int columnIndex = 0; columnIndex < 8; columnIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
                if(cellCandidateList.size() == 0 || cellCandidateList.size() != 2) {
                    continue;
                }
                int searchResult = this.findAnotherPairInRow(cellCandidateList, columnIndex + 1, rowIndex);
                if(searchResult > 0) {
                    List<Integer> nakedPairsList = new ArrayList<Integer>(2);
                    nakedPairsList.add(columnIndex);
                    nakedPairsList.add(searchResult);
                    if(!this.deleteNakedPairsFromRow(cellCandidateList, nakedPairsList, rowIndex)) {
                        this.dataModel.setUpdateCandidateListsNeeded(false);
                        continue;
                    }
                    System.out.println("Found Naked Pairs: " + cellCandidateList.toString() + ", in row: " + rowIndex + "; columns: " + columnIndex + " and " + searchResult);
                    this.dataModel.setUpdateCandidateListsNeeded(false);
                    return true;
                }
            }
        }
        // check columns:
        for(int columnIndex = 0; columnIndex < 8; columnIndex++) {
            for(int rowIndex = 0; rowIndex < 9; rowIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAt(rowIndex, columnIndex);
                if(cellCandidateList.size() == 0 || cellCandidateList.size() != 2) {
                    continue;
                }
                int searchResult = this.findAnotherPairInColumn(cellCandidateList, rowIndex + 1, columnIndex);
                if(searchResult > 0) {
                    List<Integer> nakedPairsList = new ArrayList<Integer>(2);
                    nakedPairsList.add(rowIndex);
                    nakedPairsList.add(searchResult);
                    if(!this.deleteNakedPairsFromRow(cellCandidateList, nakedPairsList, columnIndex)) {
                        this.dataModel.setUpdateCandidateListsNeeded(false);
                        continue;
                    }
                    System.out.println("Found Naked Pairs: " + cellCandidateList.toString() + ", in row: " + rowIndex + "; rows: " + columnIndex + " and " + searchResult);
                    this.dataModel.setUpdateCandidateListsNeeded(false);
                    return true;
                }
            }
        }
        // check box:
        for(int boxIndex = 0; boxIndex < 9; boxIndex++) {
            for(int boxCellIndex = 0; boxCellIndex < 9; boxCellIndex++) {
                List<Integer> cellCandidateList = this.dataModel.getCandidateListAtBox(boxIndex, boxCellIndex);
                if(cellCandidateList.size() == 0 || cellCandidateList.size() != 2) {
                    continue;
                }
                int searchResult = this.findAnotherPairInBox(cellCandidateList, boxCellIndex + 1, boxIndex);
                if(searchResult > 0) {
                    List<Integer> nakedPairsList = new ArrayList<Integer>(2);
                    nakedPairsList.add(boxCellIndex);
                    nakedPairsList.add(searchResult);
                    if(!this.deleteNakedPairsFromBox(cellCandidateList, nakedPairsList, boxIndex)) {
                        this.dataModel.setUpdateCandidateListsNeeded(false);
                        continue;
                    }
                    System.out.println("Found Naked Pairs: " + cellCandidateList.toString() + ", in box: " + boxIndex + "; cells: " + boxCellIndex + " and " + searchResult);
                    this.dataModel.setUpdateCandidateListsNeeded(false);
                    return true;
                }
            }
        }
        return false;
    }

}
