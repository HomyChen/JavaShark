package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Homy Chen on 2017-04-03.
 */
public class UsageStatController extends SubController implements Initializable{
    private pcap currentPcap = Controller.getCurrentPcap();
    @FXML
    private TableView<UsageStatRow> tblViewUsageStats;
    @FXML
    private TableColumn<UsageStatRow, String> colIPAddress;
    @FXML
    private TableColumn<UsageStatRow, Long> colInboundUsageBytes;
    @FXML
    private TableColumn<UsageStatRow, Long> colOutboundUsageBytes;
    @FXML
    private TableColumn<UsageStatRow, Double> colInboundUsagePer;
    @FXML
    private TableColumn<UsageStatRow, Double> colOutboundUsagePer;

    public ObservableList<UsageStatRow> enteredUsageStatItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            HashMap<String, ArrayList<Long>> rows = currentPcap.getUsageStat();
            Set<String> ipAddresses = rows.keySet();
            long totalData = currentPcap.getTotalData();
            for(String ip : ipAddresses){
                String ipStripped = ip.replaceAll("/", "");
                long inbound = rows.get(ip).get(0);
                long outbound = rows.get(ip).get(1);
                String inboundPer = String.format("%.3f", (((double)inbound)/(totalData))*100);
                String outboundPer = String.format("%.3f", (((double)outbound)/(totalData))*100);
                enteredUsageStatItems.add(new UsageStatRow(ipStripped, inbound, outbound, inboundPer, outboundPer));
            }
        } catch (ExceptionReadingPcapFiles exceptionReadingPcapFiles) {
            exceptionReadingPcapFiles.printStackTrace();
        }
        colIPAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        colInboundUsageBytes.setCellValueFactory(new PropertyValueFactory<>("inboundRate"));
        colOutboundUsageBytes.setCellValueFactory(new PropertyValueFactory<>("outboundRate"));
        colInboundUsagePer.setCellValueFactory(new PropertyValueFactory<>("inboundRatePer"));
        colOutboundUsagePer.setCellValueFactory(new PropertyValueFactory<>("outboundRatePer"));

        tblViewUsageStats.getColumns().clear();
        tblViewUsageStats.setItems(enteredUsageStatItems);
        tblViewUsageStats.getColumns().addAll(colIPAddress, colInboundUsageBytes, colOutboundUsageBytes, colInboundUsagePer, colOutboundUsagePer);
    }
}
