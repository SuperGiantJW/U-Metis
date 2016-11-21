package sample;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.identity.v2.User;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.OSFactory;

import java.util.List;

/**
 * Created by Joe on 11/17/2016.
 */
public class login {
    OSClient.OSClientV2 os = OSFactory.builderV2()
                            .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                            .credentials("admin", "cis347")     //second parameter might be "test" instead
                            .tenantName("admin")
                            .authenticate();

    //test queries from openstack4j to see if we're on the right track
    // Find all running Servers
    List<? extends Server> servers = os.compute().servers().list();

    // List all Networks
    List<? extends Network> networks = os.networking().network().list();

    // Find all Users
    List<? extends User> users = os.identity().users().list();

    // Find all Compute Flavors
    List<? extends Flavor> flavors = os.compute().flavors().list();

    // List all Images (Glance)
    List<? extends Image> images = os.images().list();






}
