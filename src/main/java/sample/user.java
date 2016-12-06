package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

/**
 * Created by Joe on 12/1/2016.
 */
public class user {

    @FXML
    TextField username_text;
    @FXML
    TextField password_box;
    String id = username_text.getText();
    String pwd = password_box.getText();
    private OSClient.OSClientV2 _os = OSFactory.builderV2()
            .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials(id, pwd)
                .tenantName(id)
                .authenticate();

    //   System.out.println(id + "\r\n" + pwd);
}
