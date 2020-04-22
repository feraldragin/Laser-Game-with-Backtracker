package lasers.backtracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private int COLDIM;
    private int ROWDIM;
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
            for (int i = 0; i < COLDIM; i++) {
                safeArray[lines - 1][i] = parts[i];
            }
        }
    }

    private SafeConfig(SafeConfig other){
        this.row = other.row;
        this.col = other.col;
        if (this.col == COLDIM-1){
            this.row+=1;
            this.col = 0;
        }
        else {
            this.col = other.col + 1;
            this.row = other.row;
        }
        this.safeArray = new String[other.ROWDIM][other.COLDIM];
        for (int row = 0; row < other.ROWDIM; row++) {
            for (int col = 0; col < other.COLDIM; col++) {
                this.safeArray[row][col] = other.safeArray[row][col];
            }
        }
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        // TODO
        ArrayList<Configuration> successors = new ArrayList<>();
        if (this.row == this.ROWDIM-1 && this.col == this.COLDIM-1){
            return successors;
        }
        SafeConfig Lconfig = new SafeConfig(this);
        Lconfig.safeArray[this.row][this.col] = "L";
        successors.add(Lconfig);
        SafeConfig defConfig = new SafeConfig(this);
        successors.add(defConfig);
        return successors;
    }

    @Override
    public boolean isValid() {
        // TODO
        return false;
    }

    @Override
    public boolean isGoal() {
        // TODO
        return false;
    }
}
