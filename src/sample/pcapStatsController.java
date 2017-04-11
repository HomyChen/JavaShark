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
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.packetInfo;

/**
 * Created by Iris-book on 4/9/2017.
 */
public class pcapStatsController extends SubController implements Initializable{
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
            String udpPer = String.format("%.2f", currentPcap.getUdpPer());
            String tcpPer = String.format("%.2f", currentPcap.getTcpPer());
            String totalData = String.valueOf(currentPcap.getTotalData());
            String totalPacket = String.valueOf(currentPcap.getPacketCount());
            String dataRate = String.format("%.2f", currentPcap.getDataRate());
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
}
