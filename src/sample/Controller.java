package sample;


import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.jnetpcap.packet.PcapPacket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

//import static sample.preLoader.stage;


public class Controller implements Initializable{
    @FXML
    private StackPane pane1;

    @FXML
    private Label welcome;
    @FXML
    private MenuItem pieChartShow;

    @FXML
    private TableView<packetProperty> tblViewDataItems;
    @FXML
    private TableColumn<packetProperty, Integer> colIndex;
    @FXML
    private TableColumn<packetProperty, String> colTime;
    @FXML
    private TableColumn<packetProperty, String> colSourceIp;
    @FXML
    private TableColumn<packetProperty, String> colDestinationIp;
    @FXML
    private TableColumn<packetProperty, String> colProtocol;
    @FXML
    private TableColumn<packetProperty, Integer> colLength;

    public static ObservableList<packetProperty> packetInfo = FXCollections.observableArrayList();

    private static pcap currentPcap;
    public  static pcap getCurrentPcap(){
        return currentPcap;
    }
    @FXML
    public void pieChartShow(ActionEvent e) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));


        Main.window.setScene(new Scene(root, 600, 400));


    }
    @FXML
    public  void inputFile(ActionEvent e) throws ExceptionReadingPcapFiles {
       //Main.window.setScene(Main.scene1);


        final FileChooser fileChooser = new FileChooser();
                        File file = fileChooser.showOpenDialog(Main.window);
                        if (file != null) {
                           //currentPcap = new pcap(file.getName());
                            currentPcap = new pcap(file.getPath());
                         //   pcapview testview = new pcapview(currentPcap);
                          //  testview.fileNameView();
                     ;
                            welcome.setVisible(false);
                            tblViewDataItems.setVisible(true);
                            //populate table
                            //tblViewDataItems.setItems(packetInfo);
                           // System.out.println("testing2:" + packetInfo.get(50).index + " "+ Controller.packetInfo.get(50).protocol);
                            System.out.println("testing3: size of packetInfo" + packetInfo.size());

                        }
                    }

    @Override
   public void initialize(URL location, ResourceBundle resources) {

        // Map the columns to the corresponding field names in DataItem object. We have not covered factory patterns
        // and there is all sorts of fancy stuff happening "under the hood". You just need to know that, for example,
        // the next line is connecting the colCategory column to the category field in a DataItem.

        if(currentPcap == null){
            welcome.setVisible(true);
            tblViewDataItems.setVisible(false);
        }else{
            welcome.setVisible(false);
            tblViewDataItems.setVisible(true);
        }

        colIndex.setCellValueFactory(new PropertyValueFactory<packetProperty,Integer>("index"));
        colTime.setCellValueFactory(new PropertyValueFactory<packetProperty,String>("time"));
        colSourceIp.setCellValueFactory(new PropertyValueFactory<packetProperty,String>("IpSrc"));
        colDestinationIp.setCellValueFactory(new PropertyValueFactory<packetProperty,String>("IpDst"));
        colProtocol.setCellValueFactory(new PropertyValueFactory<packetProperty,String>("protocol"));
        colLength.setCellValueFactory(new PropertyValueFactory<packetProperty,Integer>("length"));

        // Make sure the initial TableView has no columns
        tblViewDataItems.getColumns().clear();
        // Associate the actual stored data (if there is any) with the TableView control.
        tblViewDataItems.setItems(packetInfo);
        // Add the columns to the TableView
        tblViewDataItems.getColumns().addAll(colIndex, colTime,colSourceIp,colDestinationIp,colProtocol,colLength);

    }


    //packetInfo.add(new packetProperty(i, t, FormatUtils.ip(ip.source()), FormatUtils.ip(ip.destination()), "TCP", l));

    /*@FXML
    public void addDataToTable(int i, String t, String SrcIP, String DstIP, String Prot, int l) {
        packetInfo.add(new packetProperty( i, t, SrcIP, DstIP, Prot, l));

    }*/

    /***HOMY/IRIS***/
    public void usageShow(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("usageStats.fxml"));
        Main.window.setScene(new Scene(root, 700, 400));
    }

    public void statsShow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("pcapStats.fxml"));
        Main.window.setScene(new Scene(root, 700, 400));
    }
}








