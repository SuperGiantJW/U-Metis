package umetis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class Controller {
    public void login_click(ActionEvent actionEvent) {
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials("admin", "cis347")     //second parameter might be "test" instead
                .tenantName("admin")
                .authenticate();
    }


    public void logout_click(ActionEvent actionEvent) {

    }
}
