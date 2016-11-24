package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;
import javafx.scene.control.TextField;

public class Controller {
    @FXML TextField username_text;
    @FXML TextField password_box;

    public void login_click(ActionEvent actionEvent) {
        String id = username_text.getText();
        String pwd = password_box.getText();
     //   System.out.println(id + "\r\n" + pwd);
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials(id, pwd)     //second parameter might be "test" instead
                .tenantName(id)
                .authenticate();

//        System.out.println(os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2));
    }


    public void logout_click(ActionEvent actionEvent) {

    }

    public void create_user(ActionEvent actionEvent) {
    }
}
