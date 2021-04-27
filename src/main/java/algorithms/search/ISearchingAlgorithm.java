package algorithms.search;

public interface ISearchingAlgorithm {


    Solution solve(ISearchable domain) throws Exception;

    String getName();

    Object getNumberOfNodesEvaluated();

    long measureAlgorithmTimeMillis(ISearchable domain) throws Exception;

}
