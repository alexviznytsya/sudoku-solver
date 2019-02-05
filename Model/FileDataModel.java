/* file: FileDataModel.java
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
package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class FileDataModel {

    // Class properties:
    private DataModel dataModel;

    // Default controller:
    public FileDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    // Load data form file:
    public void loadData(AlgorithmsModel algorithmsModel) {
        try {
            String singleFileLine = new String();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dataModel.getLoadFile()));
            this.dataModel.clearGameBoardValues();
            this.dataModel.clearCandidateLists();
            algorithmsModel.generateCandidateLists();
            while ((singleFileLine = bufferedReader.readLine()) != null) {
                if(singleFileLine.length() > 0) {
                    Scanner scanner = new Scanner(singleFileLine);
                    int row = scanner.nextInt();
                    int column = scanner.nextInt();
                    int value = scanner.nextInt();
                    if(row < 1 || row > 9 || column < 1 || column > 9 || value < 1 || value > 9) {
                        System.err.println("Line '" + singleFileLine + "' has row, column or value that is out of 1 to 9 range.");
                        continue;
                    }
                    List<Integer>  currentCandidateList = this.dataModel.getCandidateListAt(row - 1, column - 1);
                    if(currentCandidateList.contains(value)) {
                        dataModel.updateValue(row - 1, column - 1, value);
                    } else {
                        System.err.println("Value " + value + "is not permited for insertion into [" + row + ", " + column + "].");
                    }

                } else {
                    continue;
                }
            }
            bufferedReader.close();
            this.dataModel.setOpenFileOnLoad(false);
            this.dataModel.setLoadFile(null);
        } catch (Exception e) {
            System.err.format("Error occured during '%s' file reading.", dataModel.getLoadFile().toString());
            e.printStackTrace();
        }
    }

    // Store data to file:
    public void storeData() {
        try {
            BufferedWriter bufferedWriter = null;
            if(dataModel.getStoreFile().getName().contains(".txt")) {
                bufferedWriter = new BufferedWriter(new FileWriter(dataModel.getStoreFile()));
            } else {
               bufferedWriter = new BufferedWriter(new FileWriter(dataModel.getStoreFile() + ".txt"));
            }

            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    if(dataModel.getGameBoardValueAt(i, j) != 0) {
                        bufferedWriter.write("" + (i + 1) + " " + (j + 1) + " " + dataModel.getGameBoardValueAt(i, j));
                        bufferedWriter.newLine();
                    }
                }
            }
            bufferedWriter.close();
            this.dataModel.setStoreFile(null);
        } catch (Exception e) {
            System.err.format("Error occured during '%s' file writing.", dataModel.getStoreFile().toString());
            e.printStackTrace();
        }

    }
}
