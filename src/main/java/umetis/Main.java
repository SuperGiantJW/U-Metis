package umetis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.identity.v2.User;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.OSFactory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("UMetis.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UMetis.fxml"));
        primaryStage.setTitle("U-Metis");
        primaryStage.setScene(new Scene(root, 1286, 775));
        primaryStage.show();
    }


    public static void main(String[] args) {

    }


}
