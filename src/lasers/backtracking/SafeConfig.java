package lasers.backtracking;

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

    private int[][] safeArray;
    private static int ROWDIM;
    private static int COLDIM;
    private int row;
    private int col;

    public SafeConfig(String filename) {
        // TODO
        Scanner in = new Scanner(filename);
        ROWDIM = in.nextInt();
        COLDIM = in.nextInt();
        this.safeArray = new int[ROWDIM][COLDIM];
        for (int row=0; row<ROWDIM; ++row) {
            for (int col=0; col<COLDIM; ++col) {
                this.safeArray[row][col] = in.nextInt();
            }
        }
    }

    public SafeConfig(SafeConfig other){
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
        this.safeArray = new int[ROWDIM][COLDIM];
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
        if (this.row == this.ROWDIM){
            return successors;
        }
        return null;
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
