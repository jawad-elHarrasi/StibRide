package atl.view;

import atl.model.dto.FavoriteDto;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;
import atl.presenter.Presenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class StibView {

    private Presenter presenter;

    @FXML
    private SearchableComboBox<StopsDto> destinationInput;

    @FXML
    private TableView<StopsDto> table;

    @FXML
    private SearchableComboBox<StopsDto> sourceInput;

    @FXML
    private TextField nameFavorite;

    @FXML
    private Button submitNormal;

    @FXML
    private Button addFavorite;

    @FXML
    private SearchableComboBox<FavoriteDto> favoriteInput;

    @FXML
    private Button submitFavorite;

    @FXML
    private Button removeFavorite;


    @FXML
    private void handleAddButtonAction() {
        table.getItems().clear();
        presenter.buildShortestPath();
    }

    public void setPresenter(Presenter presenter) throws StibException {
        if (presenter == null) {
            throw new StibException("View error : null presenter");
        }
        this.presenter = presenter;
    }


    public void initialize(List<StopsDto> stops, List<FavoriteDto> favoriteDtos) {
        ObservableList<StopsDto> observableStops = FXCollections.observableArrayList(stops);
        destinationInput.setItems(observableStops);
        sourceInput.setItems(observableStops);

        if (favoriteDtos != null) {
            ObservableList<FavoriteDto> observableStopsF = FXCollections.observableArrayList(favoriteDtos);
            favoriteInput.setItems(observableStopsF);
        }
    }


    public StopsDto getDestinationInput() {
        return destinationInput.getValue();
    }

    public String getFavoriteInputString() {
        return favoriteInput.getValue().toString();
    }

    public FavoriteDto getFavoriteInput(){
        return favoriteInput.getValue();
    }

    public void updateFavorite(List<FavoriteDto> favoriteDtos) {
        ObservableList<FavoriteDto> observableStops = FXCollections.observableArrayList(favoriteDtos);
        favoriteInput.setItems(observableStops);
    }

    public StopsDto getSourceInput() {
        return sourceInput.getValue();
    }

    public String getFavoriteName() {
        return nameFavorite.getText();
    }

    public void changeTableValues(List<StopsDto> shortestPath) {
        table.getColumns().clear();

        TableColumn<StopsDto, String> nameColumn = new TableColumn<>("Stations");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameStation"));

        TableColumn<StopsDto, Integer> idLineColumn = new TableColumn<>("Lines");
        idLineColumn.setCellValueFactory(new PropertyValueFactory<>("Ids_Line"));
        table.getColumns().addAll(nameColumn, idLineColumn);
        table.setItems(FXCollections.observableArrayList(shortestPath));
    }

    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addFavoriteAction(ActionEvent event) throws StibException {
        presenter.addToFavorite();
    }

    public void removeFavoriteAction(ActionEvent event) throws StibException {
        presenter.removeFavorite();
    }

    public void searchFavoriteAction(ActionEvent event) {
        table.getItems().clear();
        presenter.buildShortestFavoritePath();
    }
}
