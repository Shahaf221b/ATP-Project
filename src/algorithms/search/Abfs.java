package algorithms.search;

import java.util.ArrayList;
import java.util.Queue;

public abstract class Abfs extends ASearchingAlgorithm {

    protected Queue<AState> queue;

    public abstract int getCost(ISearchable domain, AState s);

    public abstract Queue<AState> getStruct();

    @Override
    public Solution solve(ISearchable domain) throws Exception {

        domain.initAllStates();
        this.solutionArray = new ArrayList<>();

        AState startState = domain.getStartState();
        domain.updateVisited(startState);
        startState.setCost(0);

        ArrayList<AState> stateSuccessors = domain.getAllSuccessors(startState);
        AState goalState = domain.getGoalState();

        queue = getStruct();


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
                if(solutionArray==null || solutionArray.size()==0){
                    throw new Exception("BFS algorithm could not solve the given maze");
                }
                return new Solution(solutionArray);
            }
            // adding the state's successors into the stack
            stateSuccessors = domain.getAllSuccessors(state);
            for (AState newState : stateSuccessors) {
                if (newState.notVisited()) {
                    domain.updateVisited(newState);
                    domain.updateParent(newState, state);
                    newState.setCost(state.getCost() + getCost(domain, newState));
                    queue.add(newState);
                } else if (newState.getCost() > state.getCost() + getCost(domain, newState)) {
                    if (newState.notVisited()) {
                        domain.updateVisited(newState);
                    }
                    newState.setParent(state);
                    newState.setCost(state.getCost() + getCost(domain, newState));
                }
            }
        }
        return null;
    }
}