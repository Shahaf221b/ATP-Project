package algorithms.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public abstract class ABFS extends ASearchingAlgorithm {

    protected Queue<AState> queue;

    @Override
    public Solution solve(ISearchable domain) {

        domain.initAllStates();
        this.solutionArray = new ArrayList<>();

        AState startState = domain.getStartState();
        domain.updateVisited(startState);
        startState.setCost(0);

        ArrayList<AState> stateSuccessors = domain.getAllPossibleStates(startState);


        AState goalState = domain.getGoalState();

        queue = getStruct();
//        queue.add(startState);


        // inserting the start state's successors
        for (AState state : stateSuccessors) {
            domain.updateVisited(state);
            domain.updateParent(state, startState);
            queue.add(state);
        }

        // looping through the state's successors
        while (queue.size() > 0) {
            AState state = queue.remove();
            if (state.equals(goalState)) {
                solutionArray = getPathFromParents(state);
                return new Solution(solutionArray);
            }
            // adding the state's successors into the stack
            stateSuccessors = domain.getAllPossibleStates(state);
            for (AState newState : stateSuccessors) {
                if (!newState.isVisited()) {
                    domain.updateVisited(newState);
                    domain.updateParent(newState, state);
                    newState.setCost(state.getCost() + getCost(domain,newState));
                    queue.add(newState);
                }
                else if(newState.getCost() > state.getCost()+getCost(domain,newState)){
                    if(!newState.isVisited()){
                        domain.updateVisited(newState);
                    }
                    newState.setParent(state);
                    newState.setCost(state.getCost()+getCost(domain,newState));
                }
            }
        }

        return null;
    }

    public abstract int getCost(ISearchable domain,AState s);

    public abstract Queue<AState> getStruct();
}
