package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Homy Chen on 2017-04-11.
 */
public class LineChartController extends SubController implements Initializable{

    @FXML
    private LineChart lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Integer> lineChartData = new ArrayList<>();
        try {
            lineChartData = currentPcap.getTrafficData();
        } catch (ExceptionReadingPcapFiles exceptionReadingPcapFiles) {
            exceptionReadingPcapFiles.printStackTrace();
        }
        lineChart.setTitle("Network Traffic Over Time");
        //defining a series
        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("Traffic Data");
        //populating the series with data
        for(int i = 0; i<lineChartData.size(); i++){
            series.getData().add(new XYChart.Data(String.valueOf(i), (lineChartData.get(i)/1e6)));
        }
        lineChart.getData().add(series);
    }
}
