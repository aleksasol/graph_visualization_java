package model;

public class Edge {
    private String name;
    private Node source;
    private Node target;
    private double weight;

    public Edge(String name, Node source, Node target, double weight) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

    public String getStringWeight() {
        return ((Double) weight).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}