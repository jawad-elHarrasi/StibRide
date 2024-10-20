package atl.presenter;

import atl.model.dijkstra.StationsResearcher;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;
import atl.view.StibView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Presenter implements PropertyChangeListener {

    private final StibView view;
    private final StationsResearcher stationsResearcher;

    public Presenter(StibView view, StationsResearcher stationsResearcher) throws StibException {
        if (view == null || stationsResearcher == null) {
            throw new StibException("Presenter error : null params");
        }
        this.view = view;
        this.stationsResearcher = stationsResearcher;
        initialize();
    }

    public void initialize() throws StibException {
        stationsResearcher.addObserver(this);
        view.setPresenter(this);
        view.initialize(stationsResearcher.getAllStops(), stationsResearcher.getAllFavorite());

    }


    public void buildShortestPath() {
        try {
            stationsResearcher.findShortestPath(view.getSourceInput(), view.getDestinationInput());
        } catch (StibException e) {
            view.displayError(e.getMessage());
        }
    }

    public void buildShortestFavoritePath() {
        try {
            stationsResearcher.findShortestPath(view.getFavoriteInput().getSource(), view.getFavoriteInput().getDestination());
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }


    public void addToFavorite() throws StibException {
        if (view.getFavoriteName() == null || view.getSourceInput() == null || view.getDestinationInput() == null){
            view.displayError("invalid input");
        }
        stationsResearcher.addFavorite(view.getFavoriteName().toString(), view.getSourceInput(), view.getDestinationInput());
        view.updateFavorite(stationsResearcher.getAllFavorite());
    }

    public void removeFavorite() throws StibException {
        if (view.getFavoriteInputString() == null) {
            view.displayError("invalid input");
        }
        stationsResearcher.removeFavorite(view.getFavoriteInputString());
        view.updateFavorite(stationsResearcher.getAllFavorite());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("NEW PATH")) {
            Object newVal = evt.getNewValue();
            if (newVal instanceof List) {
                view.changeTableValues((List<StopsDto>) newVal);
            } else {
                view.displayError("invalid type");
            }

        }
    }
}
