package algorithms.search;
import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> solutionPath;

    public Solution(ArrayList<AState> solutionPath)  {
        if (solutionPath != null){
            this.solutionPath = solutionPath;
        }
    }

    public ArrayList<AState> getSolutionPath(){

        return this.solutionPath;
    };
}
