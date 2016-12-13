package sample;

import com.google.common.collect.Table;

import java.awt.*;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.accessibility.AccessibleComponent;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.ext.AvailabilityZone;
import org.openstack4j.model.identity.v2.Tenant;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.options.PortListOptions;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.internal.OSAuthenticator;
import org.openstack4j.openstack.internal.OSClientSession;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;
import org.openstack4j.openstack.networking.domain.NeutronPort;
import org.openstack4j.openstack.networking.domain.NeutronSubnet;


public class Controller {

    @FXML TextField username_text;
    @FXML TextField password_box;
    @FXML Label failed_login;
    @FXML TextField company_text;
    @FXML TextField desc_text;
    @FXML TableView network_table;
    @FXML TableView subnet_table;
    @FXML TableView port_table;
    @FXML Pane login_pane;
    @FXML Pane main_pane;
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

    // initialize happens after the constructor of Controller and after all JavaFX controls are instantiated.
    // JavaFX scans the Controller class and automatically finds this at **runtime**.
    public void initialize()
    {
        // This sets up the event handler for the network_table's currently selected row being changed.
        network_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                subnet_table.getSelectionModel().clearSelection();

                // Set subnet_table's items to be the subnets of the newly selected NeutronNetwork.
                subnet_table.itemsProperty().setValue(new ObservableListWrapper<>(((NeutronNetwork)newSelection).getSubnets()));

                // Set port_table's items to ports assigned to the network by the network ID.
                port_table.itemsProperty().setValue(new ObservableListWrapper<>(_os.networking().port().list(PortListOptions.create().networkId(((NeutronNetwork)newSelection).getId()))));
            }
        });
    }


    public void login_click(ActionEvent actionEvent) {
        String id = username_text.getText();
        String pwd = password_box.getText();
        //
//        if (_os != null /*and equal to existing user*/) {
//            _os = OSFactory.builderV2()
//                    .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
//                    .credentials(id, pwd)
//                    .tenantName(id)
//                    .authenticate();
//
//            login_pane.setVisible(false);
//            main_pane.setVisible(true);
//        }
        // Test data
        if (!id.equals("") && !pwd.equals("")) {
            try {
                _os = OSFactory.builderV2()
                    .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                    .credentials(id, pwd)
                    .tenantName(id)
                    .authenticate();

//                _os = OSFactory.builderV2()
//                        .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
//                        .credentials("admin", "cis347")
//                        .tenantName("admin")
//                        .authenticate();
            } catch (AuthenticationException ex) {
                failed_login.setText("*Invalid username or password*");
                return;

            }
            failed_login.clear();
        }
        else {
            failed_login.setText("*Username and password required*");
            return;
        }

        login_pane.setVisible(false);
        main_pane.setVisible(true);
        username_text.clear();
        password_box.clear();


//        String flavors = _os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2).get();
//        System.out.println(_os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2).get());
    }

    public void create_user(MouseEvent actionEvent) {

        String company = company_text.getText();
        String description = desc_text.getText();
        //Use scanner once user input implemented
        //Scanner scan = new Scanner(System.in);
        //String s = scan.next();

        //Create a new user with hard-coded tenantId, name, pw, email, and boolean value for enabled/disabled
        _os.identity().users().create("admin", "user1", "user1password", "user1@email.com", true);

        Tenant tenant = _os.identity().tenants()
                .create(Builders.identityV2().tenant()
                        .name(company)
                        .description(description)
                        .build());
    }

    public void logout_click(MouseEvent actionEvent) {
//        _os.;


        main_pane.setVisible(false);
        login_pane.setVisible(true);
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

//    public void flavorCreate_click(MouseEvent actionEvent) {
//        Flavor flavor = Builders.flavor()
//                .name("Large Resources Template")
//                .ram(4096)
//                .vcpus(6)
//                .disk(120)
//                .rxtxFactor(1.2f)
//                .build();
//
//        flavor = _os.compute().flavors().create(flavor);

//        image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(true);
//    }

//    // Find all Flavors
//    public void flavors_click (MouseEvent actionEvent) {
//        List<Flavor> flavors = (List<Flavor>) _os.compute().flavors().list();

//        image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(true);
//    }

//public void flavorDelete_click(MouseEvent actionEvent) {
//    _os.compute().flavors().delete("flavorId");
//
//        image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(true);
//}

//   public void vmCreate_click(MouseEvent actionEvent){
//       // Create a Server Model Object
//       ServerCreate sc = Builders.server().name("UMetisTestServer").flavor("flavorId").image("imageId").build();
//
////    Boot the Server
//       Server server = _os.compute().servers().boot(sc);

//       image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
    //      vm_pane.setVisible(true);
//   }

//    public void vm_click(MouseEvent actionEvent) {
//        // List all Servers
//        List<? extends Server> servers = _os.compute().servers().list();

//       image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
        //      vm_pane.setVisible(true);
//    }

//    public void vmDelete_click(MouseEvent actionEvent){
//        _os.compute().servers().delete("serverId");
//       image_pane.setVisible(false);
//        topology_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
    //      vm_pane.setVisible(true);
   // }

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
        List<? extends Image> images = _os.compute().images().list();
//
//        // Get an Image by ID
        Image img = _os.compute().images().get("imageId");

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

        ObservableListWrapper<Subnet> obsSubnet = new ObservableListWrapper<>(subnets.stream()
                .filter(subnet -> subnet.getClass().isAssignableFrom(NeutronSubnet.class))
                .collect(Collectors.toList()));

        subnet_table.itemsProperty().setValue(obsSubnet);

        ObservableListWrapper<Port> obsPort = new ObservableListWrapper<>(ports.stream()
                .filter(port -> port.getClass().isAssignableFrom(NeutronPort.class))
                .collect(Collectors.toList()));

        port_table.itemsProperty().setValue(obsPort);

        instance_pane.setVisible(false);
        image_pane.setVisible(false);
        router_pane.setVisible(false);
        topology_pane.setVisible(false);
        network_pane.setVisible(true);

    }

    public void create_net(MouseEvent actionEvent) {

//        Network network =_os.networking().network()
//                .create(Builders.network().name("ext_network").tenantId(tenant.getId()).build());
    }

    public void delete_net(MouseEvent actionEvent) {

        _os.networking().network().delete("networkId");
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
     //   subnet.setCidr("");
        networks.add(network);

        return networks;
    }


}
