package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    AState getStartState();
    AState getGoalState();
    ArrayList<AState> getAllPossibleStates(AState s);
    int getCost(AState state); //
    void initAllStates();
    void updateVisited(AState s);
    void updateParent(AState s, AState parent);

}