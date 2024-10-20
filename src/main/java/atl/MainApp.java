package atl;

import atl.model.dijkstra.StationsResearcher;
import atl.presenter.Presenter;
import atl.view.StibView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;


public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();
        StibView stibView  = loader.getController();
        Scene scene = new Scene(root);

        StationsResearcher stationsResearcher = new StationsResearcher();
        Presenter presenter = new Presenter(stibView,stationsResearcher);

        stage.setScene(scene);
        stage.show();

    }
}
