package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("UMetis.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UMetis.fxml"));
        primaryStage.setTitle("U-Metis");
        primaryStage.setScene(new Scene(root, 1286, 775));
        primaryStage.show();
    }

}
