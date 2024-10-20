package atl.model.dijkstra;

import atl.model.dao.FavoriteRepository;
import atl.model.dao.StopsRepository;
import atl.model.dto.FavoriteDto;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;
import com.sun.prism.paint.Stop;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class StationsResearcher {

    private final Graph<StopsDto> graph;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    public StationsResearcher() throws StibException {
        this.graph = new Graph<>();
        addStopsToGraph();
    }
    private void addStopsToGraph() throws StibException {
        StopsRepository stopsRepository = new StopsRepository();
        List<StopsDto> stopsDtos = stopsRepository.selectAll();
        Set<Integer> addedStations = new HashSet<>();

        for (StopsDto stop : stopsDtos) {
            if (!addedStations.contains(stop.getId_station())) {
                graph.addNode(stop);
                addedStations.add(stop.getId_station());
            }
        }

        for (int i = 0; i < stopsDtos.size() - 1; i++) {
            StopsDto currentStop = stopsDtos.get(i);
            StopsDto nextStop = stopsDtos.get(i + 1);

            if (currentStop.getId_Line().equals(nextStop.getId_Line())) {
                graph.addEdge(currentStop, nextStop);
            }
        }
    }

    public void findShortestPath(StopsDto source, StopsDto destination) throws StibException {
        if (source == null || destination == null){
            throw new StibException("Dijkstra error : null param");
        }

        Map<StopsDto, Integer> distances = new HashMap<>();
        Map<StopsDto, StopsDto> predecessors = new HashMap<>();
        PriorityQueue<StopsDto> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));


        for (StopsDto node : graph.getAllNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            StopsDto current = priorityQueue.poll();

            for (StopsDto neighbor : graph.getAdjacentNodes(current)) {
                int weight = graph.getEdgeWeight(current, neighbor);
                int distanceThroughCurrent = distances.get(current) + weight;

                if (distanceThroughCurrent < distances.get(neighbor)) {
                    distances.put(neighbor, distanceThroughCurrent);
                    predecessors.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        List<StopsDto> shortestPath = new ArrayList<>();
        StopsDto current = destination;
        while (predecessors.containsKey(current)) {
            shortestPath.add(current);
            current = predecessors.get(current);
        }
        shortestPath.add(source);
        Collections.reverse(shortestPath);

        support.firePropertyChange("NEW PATH",null, shortestPath);
    }
    public List<StopsDto> getAllStops() throws StibException {
        StopsRepository stopsRepository = new StopsRepository();
        return stopsRepository.selectAllWithOutDouble();
    }

    public List<FavoriteDto> getAllFavorite() throws StibException{
        FavoriteRepository favoriteRepository = new FavoriteRepository();
        return favoriteRepository.selectAll();
    }
    public void addObserver(PropertyChangeListener observer) {
        support.addPropertyChangeListener(observer);
    }

    public void addFavorite(String name, StopsDto source, StopsDto destination) throws StibException {
        FavoriteRepository favoriteRepository = new FavoriteRepository();

        FavoriteDto favoriteDto = new FavoriteDto(name,source,destination);

        favoriteRepository.insert(favoriteDto);
    }
    public void removeFavorite(String fav) throws StibException {
        FavoriteRepository favoriteRepository = new FavoriteRepository();
        favoriteRepository.delete(fav);
    }






}
