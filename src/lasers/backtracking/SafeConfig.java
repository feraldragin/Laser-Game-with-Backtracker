package lasers.backtracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * The class represents a single configuration of a safe.  It is
 * used by the backtracker to generate successors, check for
 * validity, and eventually find the goal.
 *
 * This class is given to you here, but it will undoubtedly need to
 * communicate with the model.  You are free to move it into the lasers.model
 * package and/or incorporate it into another class.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class SafeConfig implements Configuration {

    private String[][] safeArray;
    private static int COLDIM;
    private static int ROWDIM;
    private int row;
    private int col;

    public SafeConfig(String filename) throws FileNotFoundException {
        // TODO
        int lines = 0;
        Scanner in = new Scanner(new File(filename));
        String first = in.nextLine();
        //splits the file
        String[] dim = first.split(" ");
        //creates rows and columns from each line
        ROWDIM = Integer.parseInt(dim[0]);
        COLDIM = Integer.parseInt(dim[1]);
        //adds the rows and columns to an array
        safeArray = new String[ROWDIM][COLDIM];
        //does the same while the file still has lines
        while (in.hasNextLine()) {
            String line = in.nextLine();
            lines++;
            String[] parts = line.split(" ");
            if (parts[0].isEmpty()) {
                break;
            }
            if (COLDIM >= 0) System.arraycopy(parts, 0, safeArray[lines - 1], 0, COLDIM);
        }
        this.row = 0;
        this.col = -1;
    }

    //copy constructor
    private SafeConfig(SafeConfig other){
        this.row = other.row;
        this.col = other.col;

        //moves the cursor throughout the board
        if (this.col == COLDIM-1){
            this.row+=1;
            this.col = 0;
        }
        else {
            this.col = other.col+1;
            this.row = other.row;
        }
        //copies the board
        this.safeArray = new String[ROWDIM][COLDIM];
        for (int row = 0; row < ROWDIM; row++) {
            this.safeArray[row] = Arrays.copyOf(other.safeArray[row], COLDIM);
        }

    }

    //creates multiple configs
    @Override
    public Collection<Configuration> getSuccessors() {
        //checks to make sure there isn't a config with a negative of column
        if (col == -1){
            col++;
        }
        //creates a default config
        SafeConfig defConfig = new SafeConfig(this);
        ArrayList<Configuration> successors = new ArrayList<>();

        if (this.row == ROWDIM-1 && this.col == COLDIM-1){
            return successors;
        }
            //creates a config with a laser
            SafeConfig Lconfig = new SafeConfig(this);
            if (!Character.isDigit(Lconfig.safeArray[Lconfig.row][Lconfig.col].charAt(0)) && !Lconfig.safeArray[Lconfig.row][Lconfig.col].equals("X")){

                        Lconfig.safeArray[Lconfig.row][Lconfig.col] = "L";
                        //creates temporary row and column variables
                        int row1 = Lconfig.row;
                        int row2 = Lconfig.row;
                        int column1 = Lconfig.col;
                        int column2 = Lconfig.col;
                        //adds a * to the left of the L
                        while (row1 >= 1) {
                            row1 -= 1;
                            //makes sure it isn't replacing something already at the coordinates
                            if (laserCheck(row1, Lconfig.col)) {
                                String laserPointer = "*";
                                Lconfig.safeArray[row1][Lconfig.col] = laserPointer;
                            } else {
                                break;
                            }
                        }
                        //adds a * to the right of the the L
                        while (row2 < Lconfig.safeArray.length - 1) {
                            row2++;
                            //makes sure it isn't replacing something already at the coordinates
                            if (laserCheck(row2, Lconfig.col)) {
                                String laserPointer = "*";
                                Lconfig.safeArray[row2][Lconfig.col] = laserPointer;
                            } else {
                                break;
                            }
                        }
                        //adds a * above the L
                        while (column1 >= 1) {
                            column1 -= 1;
                            //makes sure it isn't replacing something already at the coordinates
                            if (laserCheck(Lconfig.row, column1)) {
                                String laserPointer = "*";
                                Lconfig.safeArray[Lconfig.row][column1] = laserPointer;
                            } else {
                                break;
                            }
                        }
                        //adds a * below the L
                        while (column2 < Lconfig.safeArray[0].length - 1) {
                            column2++;
                            //makes sure it isn't replacing something already at the coordinates
                            if (laserCheck(Lconfig.row, column2)) {
                                String laserPointer = "*";
                                Lconfig.safeArray[Lconfig.row][column2] = laserPointer;
                            } else {
                                break;
                            }
                        }
                            //adds the config to the array list
                            successors.add(Lconfig);

                }
        //adds the config to the array list
        successors.add(defConfig);

        return successors;

    }

    //makes sure the index is valid
    @Override
    public boolean isValid() {
        if (this.safeArray[this.row][this.col].equals("L")) {
            //creates temporary row and column variables
            int row1 = this.row;
            int row2 = this.row;
            int column1 = this.col;
            int column2 = this.col;
            //checks if two lasers cross
            while (row1 >= 1) {
                row1 -= 1;
                //breaks if there's a number
                if (Character.isDigit(this.safeArray[row1][this.col].charAt(0)) || safeArray[row1][this.col].equals("X")) {
                    break;
                } else if (this.safeArray[row1][this.col].equals("L")) {
                    //breaks the for loop
                    return false;
                }
            }
            //checks if two lasers cross
            while (row2 < this.safeArray[0].length - 1) {
                row2++;
                if (Character.isDigit(this.safeArray[row2][this.col].charAt(0)) || safeArray[row2][this.col].equals("X")) {
                    break;
                } else if (this.safeArray[row2][this.col].equals("L")) {
                    return false;
                }
            }
            //checks if two lasers cross
            while (column1 >= 1) {
                column1 = column1 - 1;
                if (Character.isDigit(this.safeArray[this.row][column1].charAt(0)) || this.safeArray[this.row][column1].equals("X")) {
                    break;
                } else if (this.safeArray[this.row][column1].equals("L")) {
                    return false;
                }
            }
            //checks if two lasers cross
            while (column2 < this.safeArray.length - 1) {
                column2++;
                if (Character.isDigit(this.safeArray[this.row][column2].charAt(0)) || this.safeArray[this.row][column2].equals("X")) {
                    break;
                } else if (this.safeArray[this.row][column2].equals("L")) {
                    return false;
                }
            }
        }

        //makes sure the board is valid at the end
        if (this.row == ROWDIM-1 && this.col == COLDIM-1) {
            for (int rows = 0; rows < this.safeArray.length; rows++) {
                for (int columns = 0; columns < this.safeArray[rows].length; columns++) {
                    //checks for .
                    if (this.safeArray[rows][columns].equals(".")) {
                        return false;
                    }
                    //checks to make sure the number of lasers around the pillar
                    else if (Character.isDigit(this.safeArray[rows][columns].charAt(0))) {
                        int laserCount = laserCounter(rows, columns);

                        if (laserCount != Integer.parseInt(this.safeArray[rows][columns])) {
                            return false;
                        }
                    }

                }
            }
        }


        return true;
    }

    //makes sure the whole board is valid
    @Override
    public boolean isGoal() {
        //checks if the cursor is at the end of the board
        if (this.row == ROWDIM-1 && this.col == COLDIM-1){
            return true;
        }
        return false;
    }

    //counts the number of lasers around the pillar
    public int laserCounter(int rows, int columns){
        int laserCount = 0;
        //checks if a laser is to the left or above the pillar in a corner
        if (rows + 1 > this.safeArray.length-1 && columns + 1 > this.safeArray[0].length-1){
            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the left or below of the pillar in a corner
        else if (rows + 1 > this.safeArray.length-1 && columns - 1 <0){
            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the right or above of the pillar in a corner
        else if (rows - 1 < 0 && columns + 1 > this.safeArray[0].length-1){
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the right or below of the pillar in a corner
        else if (rows - 1 < 0 && columns - 1 <0){
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the left, above, or below of the pillar
        else if (rows + 1 > this.safeArray.length-1) {

            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the right, above, or below of the pillar
        else if (rows - 1 < 0){
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the right, left, or above of the pillar
        else if (columns + 1 > this.safeArray[0].length-1){
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
        }
        //checks if a laser is to the right, above, or below of the pillar
        else if (columns - 1 <0){
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }

        }
        //checks if a laser is around the pillar if the pillar isn't on an edge
        else {
            if (this.safeArray[rows + 1][columns].equals("L")) {
                laserCount++;
            }
            if (this.safeArray[rows-1][columns].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns-1].equals("L")){
                laserCount++;
            }
            if (this.safeArray[rows][columns+1].equals("L")){
                laserCount++;
            }
        }
        //returns the number of lasers around the pillar
        return laserCount;
    }

    //prints out the board
    @Override
    public String toString(){
        String result = "\n  ";
        for (int column = 0; column < safeArray[0].length; column++){
            result+=column + " ";
        }
        result += "\n  ";
        for (int i = 0; i < (safeArray[0].length*2)-1; i++){
            result+="-";
        }
        result+="\n";
        for (int row = 0; row < this.safeArray.length; row++){
            //prints the safe
            result+= row + "|";
            for (int column = 0; column < this.safeArray[row].length; column++){
                result+=this.safeArray[row][column] + " ";
            }
            result+="\n";
        }
        return result + "Cursor: " + this.row + ", " + this.col + "\n";
    }

    public boolean laserCheck(int rows, int columns){
        //checks if there is a laser at the coordinates
        if (this.safeArray[rows][columns].equals("L")){
            return false;
        }
        //checks if there is an X at the coordinates
        else if (this.safeArray[rows][columns].equals("X")){
            return false;
        }
        //checks if there is a number at the coordinates
        else if (Character.isDigit(this.safeArray[rows][columns].charAt(0))){
            return false;
        }
        else {
            return true;
        }
    }

}
