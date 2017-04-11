package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Homy Chen on 2017-04-11.
 */
public class SubController extends Controller {
    @FXML
    public  void inputFile(ActionEvent e) throws ExceptionReadingPcapFiles, IOException {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.window);

        if (file != null) {
            filename = file.getName();
            ext = filename.substring(filename.lastIndexOf(".") + 1);
            if (ext.equals("pcap")) {
                packetInfo.clear();
                currentPcap = new pcap(file.getPath());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect File Format");
                alert.setContentText("Please choose a pcap file!");
                alert.showAndWait();
            }
            try {
                this.goBackToTable(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
