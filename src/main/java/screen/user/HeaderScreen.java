package screen.user;

import controller.TourController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Tour;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HeaderScreen implements Initializable{
    @FXML
    private BorderPane userView;
    @FXML
    private Button historyBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button tourBtn;
    @FXML
    private Button trackingBtn;

    @FXML
    private Button btnNot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeBtn.setOnAction(event -> changeScene("/views/user/home.fxml"));
        historyBtn.setOnAction(event -> changeScene("/views/user/history.fxml"));
        tourBtn.setOnAction(event -> changeScene("/views/user/tour.fxml"));

        TourController tourController = new TourController();
        List<Tour> tours = null;
        try {
            tours = tourController.getByUserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Tour currentTour = tours.stream()
                .filter(tour -> tour.getStatus().toString().toLowerCase().equals("confirmed"))
                .findFirst().orElse(null);

        LocalDate nowDate = LocalDate.now();

//        if (currentTour == null || nowDate.isBefore(currentTour.getStartDate().toLocalDate()) || nowDate.isAfter(currentTour.getEndDate().toLocalDate())) {
//            trackingBtn.setOnAction(event -> changeScene("/views/user/tracknotontour.fxml"));
//        } else {
//            trackingBtn.setOnAction(event -> changeScene("/views/user/tracking.fxml"));
//        }

        logoutBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnNot.setOnAction(event -> {
            ScrollPane view = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/notification.fxml"));
                view = loader.load();

            } catch (Exception e) {
                e.printStackTrace();
            }

            BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
            userView.setCenter(view);
        });
    }

    public void changeScene(String scenePath) {
        ScrollPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        userView.setCenter(view);
    }

}

