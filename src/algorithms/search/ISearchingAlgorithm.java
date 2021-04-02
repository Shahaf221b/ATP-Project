package algorithms.search;

public interface ISearchingAlgorithm {


    public Solution solve(ISearchable domain);
    public String getName();

    Object getNumberOfNodesEvaluated();
    public long measureAlgorithmTimeMillis(ISearchable domain);

}
