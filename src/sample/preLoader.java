package sample;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Ariest on 2017-03-26.
 */
public class preLoader extends Preloader {

        static ProgressBar bar;
        static Stage stage;

        private Scene createPreloaderScene() {
            bar = new ProgressBar();
            BorderPane p = new BorderPane();
            p.setCenter(bar);
            return new Scene(p, 300, 150);
        }

        public void start(Stage stage) throws Exception {
            this.stage = stage;
           // stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("preLoader.fxml")),500,300));
            stage.setScene(createPreloaderScene());
            stage.show();

        }

       @Override
        public void handleProgressNotification(ProgressNotification pn) {
            bar.setProgress(pn.getProgress());
        }

        @Override
        public void handleStateChangeNotification(StateChangeNotification evt) {
            if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
                stage.hide();
            }
        }
    }

