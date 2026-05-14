package model;

public class Edge {
    private Node source;
    private Node target;
    private double weight;

    public Edge(Node source, Node target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Node getSource() { return source; }
    public Node getTarget() { return target; }
    public double getWeight() { return weight; }
}