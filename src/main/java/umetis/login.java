package umetis;

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
