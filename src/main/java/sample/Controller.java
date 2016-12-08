package sample;

import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javax.accessibility.AccessibleComponent;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.ext.AvailabilityZone;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.internal.OSAuthenticator;
import org.openstack4j.openstack.internal.OSClientSession;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;
import org.openstack4j.openstack.networking.domain.NeutronPort;
import org.openstack4j.openstack.networking.domain.NeutronSubnet;


public class Controller {

    @FXML TextField username_text;
    @FXML TextField password_box;
    @FXML TableView network_table;
    @FXML TableView subnet_table;
    @FXML Pane instance_pane;
    @FXML Pane image_pane;
    @FXML Pane topology_pane;
    @FXML Pane network_pane;
    @FXML Pane router_pane;
    private OSClient.OSClientV2 _os;

    private Random random = new Random();

    public Controller()
    {

    }

    public void initialize()
    {
        network_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                subnet_table.getSelectionModel().clearSelection();
                subnet_table.itemsProperty().setValue(new ObservableListWrapper<>(((NeutronNetwork)newSelection).getSubnets()));
            }
        });
    }


    public void login_click(ActionEvent actionEvent) {
        String id = username_text.getText();
        String pwd = password_box.getText();
     //   System.out.println(id + "\r\n" + pwd);
        _os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials(id, pwd)
                .tenantName(id)
                .authenticate();


//        String flavors = _os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2).get();
//        System.out.println(_os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2).get());
    }

    public void create_user(MouseEvent actionEvent) {

        //Use scanner once user input implemented
        //Scanner scan = new Scanner(System.in);
        //String s = scan.next();

        //Create a new user with hard-coded tenantId, name, pw, email, and boolean value for enabled/disabled
        _os.identity().users().create("admin", "user1", "user1password", "user1@email.com", true);

    }

    public void logout_click(MouseEvent actionEvent) {
//        _os.;
    }

//    public void btnCreateNetwork_Click(ActionEvent actionEvent) {
//        // Create a Network with hard-coded values
//        _os.networking().network().create(Builders.network().name("MyNewNet").tenantId("admin").build());
//
//    }

//    public OSClient.OSClientV2 get_os(){
//        return _os;
//    }
//
//    public void osSet(OSClient.OSClientV2 os){
//        this._os = os;
//    }

    public void instance_click(MouseEvent actionEvent) {

//        ObservableListWrapper<AvailabilityZone.NovaService> obsNetwork = new ObservableListWrapper<>(networks.stream()
//                .filter(compute -> compute.getClass().isAssignableFrom(NeutronNetwork.class))
//                .collect(Collectors.toList()));
//
//        Image img = _os.compute().images().get("imageId");
        
        image_pane.setVisible(false);
        topology_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(false);//
        instance_pane.setVisible(true);
    }

    public void image_click(MouseEvent actionEvent) {
//        // List all Images (detailed @see #list(boolean detailed) for brief)
//        List<? extends Image> images = _os.compute().images().list();
//
//        // Get an Image by ID
//        Image img = _os.compute().images().get("imageId");

        instance_pane.setVisible(false);
        topology_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(false);
        image_pane.setVisible(true);
    }

    public void topology_click(MouseEvent actionEvent) {

        instance_pane.setVisible(false);
        image_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(false);
        topology_pane.setVisible(true);
    }

    public void network_click(MouseEvent actionEvent) {

//        // List the networks which the current tenant has access to
        List<? extends Network> networks = _os.networking().network().list();
//        List<? extends Network> networks = mockNetworks();

//        // List all subnets which the current authorized tenant has access to
        List<? extends Subnet> subnets = _os.networking().subnet().list();

//        // List all Ports which the current authorized tenant has access to
        List<? extends Port> ports =_os.networking().port().list();

        ObservableListWrapper<Network> obsNetwork = new ObservableListWrapper<>(networks.stream()
                .filter(network -> network.getClass().isAssignableFrom(NeutronNetwork.class))
                .collect(Collectors.toList()));

        network_table.itemsProperty().setValue(obsNetwork);

//        ObservableListWrapper<Network> obsSubnet = new ObservableListWrapper<>(networks.stream()
//                .filter(network -> network.getClass().isAssignableFrom(NeutronNetwork.class))
//                .collect(Collectors.toList()));

        instance_pane.setVisible(false);
        image_pane.setVisible(false);
        router_pane.setVisible(false);
        topology_pane.setVisible(false);
        network_pane.setVisible(true);


    }

    public void router_click(MouseEvent actionEvent)
    {
        instance_pane.setVisible(false);
        image_pane.setVisible(false);
        topology_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(true);
    }

    public List<Network> mockNetworks()
    {
        List<Network> networks = new ArrayList<>();
        Network network = new NeutronNetwork();
        network.setId("asd2131");

        network.setName("network" + random.nextInt());

        networks.add(network);

        return networks;
    }
}
