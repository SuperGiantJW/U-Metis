package sample;

import org.junit.Test;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

import static org.junit.Assert.*;

/**
 * Created by Joe on 11/16/2016.
 */
public class MainTest {
    @Test
    public void start() throws Exception {
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://controller:5000/v2.0")    //port might be "35357" instead for admin, since 5000 was typically for the demo user
                .credentials("admin", "cis347")     //second parameter might be "test" instead
                .tenantName("admin")
                .authenticate();

        System.out.println(os.compute().flavors().list().stream().map(flavor -> flavor.getName()).reduce((f1, f2) -> f1 + "\r\n" + f2));
    }

}