package algorithms.search;

public abstract class AState implements Comparable<AState> {

    private boolean visited;
    private AState parent;
    private int costFromStart;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public AState() {
        visited = false;
    }

    public void setVisited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }

    @Override
    public abstract boolean equals(Object obj);

    public void setParent(AState s) {
        this.parent = s;
    }

    public void setCost(int num) {
        this.costFromStart = num;
    }

    public int getCost() {
        return costFromStart;
    }

    public AState getParent() {
        return parent;
    }

    @Override
    public int compareTo(AState s) {
        return (this.getCost()-s.getCost());
    }


}