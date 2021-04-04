package algorithms.search;

public abstract class AState implements Comparable<AState> {

    private boolean visited;
    private AState parent;
    private int cost;
    private String name;

    /* constructor */
    public AState() {
        visited = false;
    }

    @Override
    public abstract boolean equals(Object obj);


    /* getters and setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisited() {
        this.visited = true;
    }

    public boolean notVisited() {
        return !this.visited;
    }

    public AState getParent() {
        return parent;
    }

    public void setParent(AState s) {
        this.parent = s;
    }

    public void setCost(int num) {
        this.cost = num;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public int compareTo(AState s) {
        return (s.getCost() - this.getCost());
    }
}