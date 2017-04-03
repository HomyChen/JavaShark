package sample;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;


/**
 * Created by Ariest on 2017-04-01.
 */
public class pieChartController implements Initializable {
    @FXML
    private PieChart chart;
    @FXML
    private Button back;
   // int uc = Controller.getCurrentPcap().getUdpCount();
    //int tc = Controller.getCurrentPcap().getTcpCount();
    //bservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("udp", uc), new PieChart.Data("tcp",tc));
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        // Create a new ObservableList of PieChart.Data objects, then iterate and convert the DataItems.
       int uc = Controller.getCurrentPcap().getUdpCount();
        int tc = Controller.getCurrentPcap().getTcpCount();
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(new Data("udp", uc), new Data("tcp",tc));

        //final PieChart chart = new PieChart(pieChartData);

        //ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
       // pieChartData.add(new PieChart.Data("udp", uc));
       // pieChartData.add(new PieChart.Data("tcp",tc));


        // Display the Pie chart.
        //chart.setTitle("Categories");
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
    @FXML
    public void goBackToTable(ActionEvent e) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        Main.window.setScene(new Scene(root, 600, 400));



    }

}
