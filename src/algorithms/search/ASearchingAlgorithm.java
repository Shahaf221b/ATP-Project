package algorithms.search;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected String name;
    protected Collection<AState> struct;
    protected ArrayList<AState> solutionArray;

    @Override
    public abstract Solution solve(ISearchable domain);

    @Override
    public String getName() {
        return name;
    }

    public ArrayList<AState> getPathFromParents(AState state){
        ArrayList<AState> path = new ArrayList<>();
        while (state != null){
            path.add(0, state);
            state = state.getParent();
        }
        return path;
    }

    public long measureAlgorithmTimeMillis(ISearchable domain){
        long aTime = System.currentTimeMillis();
        solve(domain);
        long bTime = System.currentTimeMillis();
        return (bTime-aTime);
    }

    public Object getNumberOfNodesEvaluated() {
        return solutionArray.size();
    }

}
