package sample;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

/**
 * Created by Joe on 11/17/2016.
 */
public class login {


    public static void main(String[] args) {
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")
                .credentials("admin","cis347")
                .tenantName("admin")
                .authenticate();
    }
}
