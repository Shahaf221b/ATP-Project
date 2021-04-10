package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {


    public DepthFirstSearch() {
        this.name = "DepthFirstSearch";
        this.struct = new Stack<>();
        this.solutionArray = new ArrayList<>();
    }

    @Override
    public Solution solve(ISearchable domain) throws Exception {

        domain.initAllStates();

        AState startState = domain.getStartState();
        domain.updateVisited(startState);

        ArrayList<AState> stateSuccessors = domain.getAllSuccessors(startState);
        Stack<AState> stack = (Stack<AState>) this.struct;
        AState goalState = domain.getGoalState();

        stack.push(startState);

        // inserting the start state's successors
        for (AState state : stateSuccessors) {
            domain.updateVisited(state);
            domain.updateParent(state, startState);
            stack.push(state);
        }

        // looping through the state's successors
        while (stack.size() > 0) {
            AState state = stack.pop();
            if (state.equals(goalState)) {
                solutionArray = getPathFromParents(state);
                if (solutionArray == null || solutionArray.size() == 0) {
                    throw new Exception("DepthFirstSearch algorithm could not solve the given maze");
                }
                return new Solution(solutionArray);

            }
            // adding the state's successors into the stack
            stateSuccessors = domain.getAllSuccessors(state);
            for (AState newState : stateSuccessors) {
                if (newState.notVisited()) {
                    domain.updateVisited(newState);
                    domain.updateParent(newState, state);
                    stack.push(newState);
                }
            }
        }
        throw new Exception("no solution was found");
    }
}


