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
            String udpPer = String.format("%.2f", currentPcap.getUdpPer()) + "% of All Packets";
            String udpCount = String.valueOf(currentPcap.getUdpCount());
            String tcpPer = String.format("%.2f", currentPcap.getTcpPer()) + "% of All Packets";
            String tcpCount = String.valueOf(currentPcap.getTcpCount());
            String totalData = String.valueOf(currentPcap.getTotalData()) + " bytes";
            String totalPacket = String.valueOf(currentPcap.getPacketCount());
            String dataRate = String.format("%.2f", currentPcap.getDataRate()) + " bytes/s";
            String pcapTime = String.format("%.2f", currentPcap.getTotalTime()) + " seconds";
            String packetsPerSec = String.format("%.2f", (currentPcap.getPacketCount() / currentPcap.getTotalTime())) + " packets/s";
            thePcapStatItems.add(new pcapStatsRow("TCP Percentage", tcpPer));
            thePcapStatItems.add(new pcapStatsRow("UDP Percentage", udpPer));
            thePcapStatItems.add(new pcapStatsRow("Number of TCP Packets", tcpCount));
            thePcapStatItems.add(new pcapStatsRow("Number of UDP packets", udpCount));
            thePcapStatItems.add(new pcapStatsRow("Total Packet Data", totalData));
            thePcapStatItems.add(new pcapStatsRow("Total Number of Packets", totalPacket));
            thePcapStatItems.add(new pcapStatsRow("Total Time Elapsed", pcapTime));
            thePcapStatItems.add(new pcapStatsRow("Average Packets Per Second", packetsPerSec));
            thePcapStatItems.add(new pcapStatsRow("Average Data Rate", dataRate));

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
