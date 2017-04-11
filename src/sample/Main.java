package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    @FXML
    static Stage window;
    VBox layout;
    MenuBar menuBar;
    StackPane pane;
    Label label;
    TextField textField;

    @Override
    public void start(Stage stage) throws Exception{

        window = stage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        window.setTitle("Pig App");
        window.getIcons().add(new Image("application_icon_pig1.png"));
        window.setScene(new Scene(root, 1000, 700));

        window.show();
        System.out.println("-----------test1----------");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
