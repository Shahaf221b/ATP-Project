package algorithms.search;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch extends ABFS {


    public BreadthFirstSearch(){
        this.name = "BreadthFirstSearch";
        Queue<AState> queue = new LinkedList<>();
        this.struct = queue;
        this.solutionArray = new ArrayList<>();
    }
/*
    @Override
    public Solution solve(ISearchable domain) {

        domain.initAllStates();
        this.solutionArray = new ArrayList<>();

        AState startState = domain.getStartState();
        domain.updateVisited(startState);
        startState.setCost(0);

        ArrayList<AState> stateSuccessors = domain.getAllPossibleStates(startState);
        Queue<AState> queue = (LinkedList<AState>) this.struct;
        AState goalState = domain.getGoalState();

        queue.add(startState);


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
                    newState.setCost(state.getCost() + 1);
                    queue.add(newState);
                }
                else if(newState.getCost() > state.getCost()+1){
                    if(!newState.isVisited()){
                        domain.updateVisited(newState);
                    }
                    newState.setParent(state);
                    newState.setCost(state.getCost()+1);
                }
            }
        }

        return null;
    } */


    @Override
    public int getCost(ISearchable domain,AState s) {
        return 1;
    }

    @Override
    public Queue<AState> getStruct() {
        return (Queue<AState>) this.struct;
    }
}