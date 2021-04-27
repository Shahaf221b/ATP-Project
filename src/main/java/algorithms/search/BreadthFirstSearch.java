package algorithms.search;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch extends Abfs {

    public BreadthFirstSearch() {
        this.name = "BreadthFirstSearch";
        Queue<AState> queue = new LinkedList<>();
        this.struct = queue;
        this.solutionArray = new ArrayList<>();
    }

    @Override
    public int getCost(ISearchable domain, AState s) {
        return 1;
    }

    @Override
    public Queue<AState> getStruct() {
        return (Queue<AState>) this.struct;
    }
}