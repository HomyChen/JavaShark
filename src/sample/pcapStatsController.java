package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.packetInfo;

/**
 * Created by Iris-book on 4/9/2017.
 */
public class pcapStatsController implements Initializable{
    private pcap currentPcap = Controller.getCurrentPcap();

    @FXML
    private TableView<pcapStatsRow> tblViewPcapStatsRow;
    @FXML
    private TableColumn<pcapStatsRow, String> colStatsParam;
    @FXML
    private TableColumn<pcapStatsRow, String> colTheValue;

    public ObservableList<pcapStatsRow> thePcapStatItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            String udpPer = String.valueOf(currentPcap.getUdpPer());
            String tcpPer = String.valueOf(currentPcap.getTcpPer());
            String totalData = String.valueOf(currentPcap.getTotalData());
            String totalPacket = String.valueOf(currentPcap.getPacketCount());
            String dataRate = String.valueOf(currentPcap.getDataRate());
            thePcapStatItems.add(new pcapStatsRow("TCP Percentage", tcpPer));
            thePcapStatItems.add(new pcapStatsRow("UDP Percentage", udpPer));
            thePcapStatItems.add(new pcapStatsRow("Total Packet Data (in bytes)", totalData));
            thePcapStatItems.add(new pcapStatsRow("Total # of Packets", totalPacket));
            thePcapStatItems.add(new pcapStatsRow("Data Rate (bytes per second)", dataRate));

        } catch (ExceptionReadingPcapFiles exceptionReadingPcapFiles) {
            exceptionReadingPcapFiles.printStackTrace();
        }

        colStatsParam.setCellValueFactory(new PropertyValueFactory<>("statsParam"));
        colTheValue.setCellValueFactory(new PropertyValueFactory<>("theValue"));

        tblViewPcapStatsRow.getColumns().clear();
        tblViewPcapStatsRow.setItems(thePcapStatItems);
        tblViewPcapStatsRow.getColumns().addAll(colStatsParam, colTheValue);

    }

    @FXML
    public void goBackToTable(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(new Scene(root, 600, 400));
    }

    @FXML
    public void pieChartShow(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        Main.window.setScene(new Scene(root, 600, 400));
    }
    @FXML
    public  void inputFile(ActionEvent e) throws ExceptionReadingPcapFiles {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.window);
        if (file != null) {
            currentPcap = new pcap(file.getPath());
            System.out.println("testing3: size of packetInfo" + packetInfo.size());
        }
    }

    public void usageShow(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("usageStats.fxml"));
        Main.window.setScene(new Scene(root, 700, 400));
    }

    public void statsShow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("pcapStats.fxml"));
        Main.window.setScene(new Scene(root, 700, 400));
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
