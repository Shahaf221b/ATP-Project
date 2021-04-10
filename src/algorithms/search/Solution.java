package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private final ArrayList<AState> solutionPath;

    public Solution(ArrayList<AState> solutionPath) throws Exception {
        if (solutionPath == null) {
            throw new Exception("there's no solution");
        }
        this.solutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath() {

        return this.solutionPath;
    }

    ;
}
