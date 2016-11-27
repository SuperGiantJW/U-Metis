package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.identity.v2.Tenant;
import org.openstack4j.model.identity.v2.User;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.OSFactory;
import javafx.scene.control.TextField;
import java.util.Scanner;

public class Controller {
    @FXML TextField username_text;
    @FXML TextField password_box;
    private OSClient.OSClientV2 _os;

    public void login_click(ActionEvent actionEvent) {
        String id = username_text.getText();
        String pwd = password_box.getText();
     //   System.out.println(id + "\r\n" + pwd);
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials(id, pwd)
                .tenantName(id)
                .authenticate();

        _os = os;

//        List<? extends Compute> compute = os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2));
    }


    public void logout_click(ActionEvent actionEvent) {

    }

    public void create_user(ActionEvent actionEvent) {

        //Use scanner once user input implemented
        //Scanner scan = new Scanner(System.in);
        //String s = scan.next();

        //Create a new user with hard-coded tenantId, name, pw, email, and boolean value for enabled/disabled
        _os.identity().users().create("admin", "user1", "user1password", "user1@email.com", true);

    }

    public void btnCreateNetwork_Click(ActionEvent actionEvent) {
        // Create a Network with hard-coded values
        _os.networking().network().create(Builders.network().name("MyNewNet").tenantId("admin").build());
        
    }

    public OSClient.OSClientV2 osGet(){
        return _os;
    }

    public void osSet(OSClient.OSClientV2 os){
        this._os = os;
    }

    public void instance_click(ActionEvent actionEvent) {
    }

    public void image_click(ActionEvent actionEvent) {
    }

    public void topology_click(ActionEvent actionEvent) {
    }

    public void network_click(ActionEvent actionEvent) {
    }

    public void router_click(ActionEvent actionEvent) {
    }
}
