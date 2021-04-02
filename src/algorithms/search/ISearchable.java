package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    boolean isGoalState(AState state);
    ArrayList<AState> getAllPossibleStates(AState s);
    int getCost(AState state); // TODO: NEW
    void initAllStates();
    void updateVisited(AState s);
    void updateParent(AState s, AState parent);
    void setState(AState state);
}