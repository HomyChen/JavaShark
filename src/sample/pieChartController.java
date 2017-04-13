package sample;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
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
import java.util.stream.Collectors;

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

        DoubleBinding total = Bindings.createDoubleBinding(() ->
                pieChartData.stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)), pieChartData);

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " " , String.format("%.1f%%", 100*data.getPieValue()/total.get())
                        )
                )
        );
    }

}
