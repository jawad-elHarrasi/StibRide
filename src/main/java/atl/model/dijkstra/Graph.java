package atl.model.dijkstra;

import javafx.util.Pair;

import java.util.*;

public class Graph<T> {
    private final Map<T, List<T>> adjacencyList;
    private final Map<Pair<T, T>, Integer> edgeWeights;

    public Graph() {
        adjacencyList = new HashMap<>();
        edgeWeights = new HashMap<>();
    }

    public void addNode(T node) {
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    public void addEdge(T source, T destination) {
        addNode(source);
        addNode(destination);

        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);

        edgeWeights.put(new Pair<>(source, destination), 1);
        edgeWeights.put(new Pair<>(destination, source), 1);
    }

    public List<T> getAdjacentNodes(T node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<T> getAllNodes() {
        return adjacencyList.keySet();
    }

    public int getEdgeWeight(T source, T destination) {
        return edgeWeights.getOrDefault(new Pair<>(source, destination), 0);
    }


    public void printGraph() {
        for (Map.Entry<T, List<T>> entry : adjacencyList.entrySet()) {
            T node = entry.getKey();
            List<T> neighbors = entry.getValue();
            System.out.print(node + " -> ");
            for (T neighbor : neighbors) {
                System.out.print(neighbor + " , ");
            }
            System.out.println();
        }
    }


}
