package model;

public class Edge {
    private String name;
    private Node source;
    private Node target;
    private double weight;

    public Edge(String name, Node source, Node target, double weight) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nazwa krawędzi nie może być pusta");
        }
        if (source == null) {
            throw new IllegalArgumentException("Węzeł źródłowy nie może być null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Węzeł docelowy nie może być null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Waga krawędzi nie może być ujemna");
        }

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
        if (name == null || name.isEmpty()) {
            System.err.println("Nazwa krawędzi nie może być pusta");
            return;
        }
        this.name = name;
    }
}