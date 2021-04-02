package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstSearch extends ABFS{

    public BestFirstSearch(){
        this.name = "BestFirstSearch";
        this.struct = new PriorityQueue<AState>();
        this.solutionArray = new ArrayList<>();
    }


    @Override
    public int getCost(ISearchable domain,AState s) {
        return domain.getCost(s);
    }

    @Override
    public Queue<AState> getStruct() {
        return (Queue<AState>) this.struct;
    }
}
