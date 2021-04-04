package algorithms.search;

public interface ISearchingAlgorithm {


    Solution solve(ISearchable domain);
    String getName();
    Object getNumberOfNodesEvaluated();

    long measureAlgorithmTimeMillis(ISearchable domain);

}
