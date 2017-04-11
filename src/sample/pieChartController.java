package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static sample.Controller.packetInfo;


/**
 * Created by Ariest on 2017-04-01.
 */
public class pieChartController extends SubController implements Initializable {

    @FXML
    private PieChart chart;
    @FXML
    private Button back;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Create a new ObservableList of PieChart.Data objects, then iterate and convert the DataItems.
       int uc = this.currentPcap.getUdpCount();
        int tc = this.currentPcap.getTcpCount();
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(new Data("udp", uc), new Data("tcp",tc));
        // Display the Pie chart.
        chart.setData(pieChartData);

        final Label caption = new Label("");

        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 24 arial;");

        for( final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }
    }

}
