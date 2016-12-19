package sample;

import com.google.common.collect.Table;

import java.awt.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
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
import org.openstack4j.model.compute.*;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.ext.AvailabilityZone;
import org.openstack4j.model.identity.v2.Role;
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

import static java.util.stream.Collectors.toList;


public class Controller {

//    @FXML
//    Button create_net_submit;
    @FXML
    Pane create_network;
    @FXML
    ChoiceBox network_state;
    @FXML
    ComboBox project_selection;
    @FXML
    ChoiceBox net_type_selection;
    @FXML
    TextField username_text;
    @FXML
    TextField password_box;
    @FXML
    Label failed_login;
    @FXML
    TextField company_text;
    @FXML
    TextField desc_text;
    @FXML
    TextField new_network;
    @FXML
    TableView network_table;
    @FXML
    TableView subnet_table;
    @FXML
    TableView port_table;
    @FXML
    Pane login_pane;
    @FXML
    Accordion admin_control;
    @FXML
    Accordion user_control;
    @FXML
    Pane main_pane;
    @FXML
    Pane vm_pane;
    @FXML
    TableView vminfo_table;
    @FXML
    TableView vmspecs_table;
    @FXML
    TableView vmipmeta_table;
    @FXML
    Pane instance_pane;
    @FXML
    ListView instanceinfo_list;
    @FXML
    ListView instancespecs_list;
    @FXML
    ListView instanceipmeta_list;
    @FXML
    Pane image_pane;
    @FXML
    Pane network_pane;
    @FXML
    Pane router_pane;
    @FXML
    Pane network_form;
    private OSClient.OSClientV2 _os;

    private Random random = new Random();
    @FXML
    TableView image_table;

//    private Network network;

    public Controller() {

    }

    // initialize happens after the constructor of Controller and after all JavaFX controls are instantiated.
    // JavaFX scans the Controller class and automatically finds this at **runtime**.
    public void initialize() {
        // This sets up the event handler for the network_table's currently selected row being changed.
        network_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                subnet_table.getSelectionModel().clearSelection();

                // Set subnet_table's items to be the subnets of the newly selected NeutronNetwork.
                subnet_table.itemsProperty().setValue(new ObservableListWrapper<>(((NeutronNetwork) newSelection).getSubnets()));

                // Set port_table's items to ports assigned to the network by the network ID.
                port_table.itemsProperty().setValue(new ObservableListWrapper<>(_os.networking().port().list(PortListOptions.create().networkId(((NeutronNetwork) newSelection).getId()))));
            }
        });

//        vminfo_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                vmspecs_table.getSelectionModel().clearSelection();
//
//                // Set subnet_table's items to be the subnets of the newly selected NeutronNetwork.
//                vmspecs_table.itemsProperty().setValue(new ObservableListWrapper<>(((NeutronNetwork) newSelection).getSubnets()));
//
//                // Set port_table's items to ports assigned to the network by the network ID.
//                vmipmeta_table.itemsProperty().setValue(new ObservableListWrapper<>(_os.networking().port().list(PortListOptions.create().networkId(((NeutronNetwork) newSelection).getId()))));
//            }
//        });
    }


    public void login_click(ActionEvent actionEvent) {
        String id = username_text.getText();
        String pwd = password_box.getText();

        // Test data
        if (!id.equals("") && !pwd.equals("")) {
            try {
                String t_tenant = id;
                if (id.equals("eric")) {
                    t_tenant = "CreateProject";
                }

                _os = OSFactory.builderV2()
                        .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                        .credentials(id, pwd)
                        .tenantName(t_tenant)
                        .authenticate();

//                _os = OSFactory.builderV2()
//                        .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
//                        .credentials("admin", "cis347")
//                        .tenantName("admin")
//                        .authenticate();
            }
            catch (AuthenticationException ex) {
                failed_login.setText("*Invalid username or password*");
                return;
            }

        } else {
            failed_login.setText("*Username and password required*");
            return;
        }

        //System.out.println(_os.getAccess().getUser().getRoles().stream().map(role -> role.getName()).reduce((r1, r2) -> r1 + "\r\n" + r2).toString());

        boolean isAdmin = false;
        for (Role role:
                _os.getAccess().getUser().getRoles()) {

            isAdmin |= role.getName().equals("admin");
        }

        login_pane.setVisible(false);
        main_pane.setVisible(true);
        admin_control.setVisible(isAdmin);
        user_control.setVisible(true);
        username_text.clear();
        password_box.clear();

    }

    public void create_user(MouseEvent actionEvent) {

        String company = company_text.getText();
        String description = desc_text.getText();
        //Use scanner once user input implemented
        //Scanner scan = new Scanner(System.in);
        //String s = scan.next();

        //Create a new user with hard-coded tenantId, name, pw, email, and boolean value for enabled/disabled
        _os.identity().users().create("admin", "user1", "user1password", "user1@email.com", true);

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
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(true);
//    }

//public void flavorDelete_click(MouseEvent actionEvent) {
//    _os.compute().flavors().delete("flavorId");
//
//        image_pane.setVisible(false);
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
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
    //      vm_pane.setVisible(true);
//    }

//    public void vmDelete_click(MouseEvent actionEvent){
//        _os.compute().servers().delete("serverId");
//       image_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
    //      vm_pane.setVisible(true);
    // }

   public void instance_click(MouseEvent actionEvent) {
//        List<? extends Image> instances = _os.compute().images().list();
//        ObservableListWrapper<compute> obsInstance = new ObservableListWrapper<>(instances.stream()
//                .filter(compute -> compute.getClass().isAssignableFrom(compute.class))
//                .collect(Collectors.toList()));
//
//        vminfo_table.itemsProperty().setValue(obsInstance);
////
////        Image img = _os.compute().images().get("imageId");
//
//        image_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        vm_pane.setVisible(false);
//        vminfo_table.setVisible(false);
//        vmspecs_table.setVisible(false);
//        vmipmeta_table.setVisible(false);
//        instance_pane.setVisible(true);
    }

    public void image_click(MouseEvent actionEvent) {
//        // List all Images (detailed @see #list(boolean detailed) for brief)
//        List<? extends Image> images = _os.compute().images().list();
////
//        ObservableListWrapper<compute> obsImage = new ObservableListWrapper<compute>(images.stream()
//                .filter(compute -> compute.getClass().isAssignableFrom(compute.class))
//                .collect(toList()));

//        image_table.itemsProperty().setValue(obsImage);

        create_network.setVisible(false);
        vm_pane.setVisible(false);
        instance_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(false);
        image_pane.setVisible(true);
    }

    public void network_click(MouseEvent actionEvent) {

//        // List the networks which the current tenant has access to
        List<? extends Network> networks = _os.networking().network().list();
//        List<? extends Network> networks = mockNetworks();

//        // List all subnets which the current authorized tenant has access to
        List<? extends Subnet> subnets = _os.networking().subnet().list();

//        // List all Ports which the current authorized tenant has access to
        List<? extends Port> ports = _os.networking().port().list();


        //Network obs1Network = _os.networking().network().get("749213b3-1b73-4ec2-90c9-2f6da3a06843");
        ObservableListWrapper<Network> obsNetwork = new ObservableListWrapper<>(networks.stream()
                .filter(network -> network.getClass().isAssignableFrom(NeutronNetwork.class))
                //.filter(network -> network.getClass().isAssignableFrom(NeutronNetwork.class))
                //.filter(network -> network.equals(network1))
                .collect(toList()));

        network_table.itemsProperty().setValue(obsNetwork);
        //network_table.itemsProperty().setValue(obs1Network);

        ObservableListWrapper<Subnet> obsSubnet = new ObservableListWrapper<>(subnets.stream()
                .filter(subnet -> subnet.getClass().isAssignableFrom(NeutronSubnet.class))
                .collect(toList()));

        subnet_table.itemsProperty().setValue(obsSubnet);

        ObservableListWrapper<Port> obsPort = new ObservableListWrapper<>(ports.stream()
                .filter(port -> port.getClass().isAssignableFrom(NeutronPort.class))
                .collect(toList()));

        port_table.itemsProperty().setValue(obsPort);

        vm_pane.setVisible(false);
        instance_pane.setVisible(false);
        image_pane.setVisible(false);
        router_pane.setVisible(false);
        network_pane.setVisible(true);

    }

    public void create_network(MouseEvent actionEvent) {

        network_pane.setVisible(false);
        create_network.setVisible(true);

    }

    public void cancel_op(ActionEvent actionEvent) {

        network_form.setVisible(false);
        network_pane.setVisible(true);
    }

    public void create_net_submit(MouseEvent actionEvent) {
//        String new_sub = new_network.getText();
//
//        // Reads from the neutron list available projects and adds them to a dropdown
//        final ComboBox projectComboBox = new ComboBox();
//        projectComboBox.getItems().addAll(
//                "Local",
//                "VLan"
//        );
//
//        final ComboBox typeComboBox = new ComboBox();
//        typeComboBox.getItems().addAll(
//                "Local",
//                "VLan"
//        );
//
//        final ComboBox stateComboBox = new ComboBox();
//        stateComboBox.getItems(network.getStatus()).addAll(
//                "UP",
//                "DOWN"
//        );
//
//        typeComboBox.setValue("Local");
//        stateComboBox.setValue("UP");
//
//
// Network network =
        _os.networking().network()
                .create(Builders.network().name("network").tenantId(_os.identity().tenants().list().stream().map(
                        tenant -> _os.identity().users().listTenantUsers(tenant.getId()).stream().filter(
                                user -> user.getId().equals(_os.getAccess().getUser().getId())).collect(toList()).get(0))
                        .collect(toList()).get(0).getId()).build());

    }

    public void delete_net(MouseEvent actionEvent) {

        _os.networking().network().delete("networkId");
    }


    public void router_click(MouseEvent actionEvent) {
        vm_pane.setVisible(false);
        instance_pane.setVisible(false);
        instanceinfo_list.setVisible(false);
        instancespecs_list.setVisible(false);
        image_pane.setVisible(false);
        network_pane.setVisible(false);
        router_pane.setVisible(true);
    }

    public List<Network> mockNetworks() {
        List<Network> networks = new ArrayList<>();
        Network network = new NeutronNetwork();
        network.setId("asd2131");
        network.setName("network" + random.nextInt());
        //   subnet.setCidr("");
        networks.add(network);

        return networks;
    }


    public void create_vm(ActionEvent actionEvent) {
       // Create a Server Model Object
       ServerCreate sc = Builders.server().name("UMetisTestServer").flavor("flavorId").image("imageId").build();

        // Boot the Server
       Server server = _os.compute().servers().boot(sc);

//       image_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
//              vm_pane.setVisible(true);


    }

    public void delete_vm(ActionEvent actionEvent) {
       _os.compute().servers().delete("serverId");

//       image_pane.setVisible(false);
//        network_pane.setVisible(false);
//        router_pane.setVisible(false);
//        instance_pane.setVisible(false);
//        flavor_pane.setVisible(false);
//        vm_pane.setVisible(true);
    }

    public void show_images(ActionEvent actionEvent) {
    }

    public void show_vms(ActionEvent actionEvent) {
    }

    public void show_flavors(ActionEvent actionEvent) {
    }

}
