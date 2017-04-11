package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
            packetInfo.clear();
            currentPcap = new pcap(file.getPath());
            try {
                this.goBackToTable(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
