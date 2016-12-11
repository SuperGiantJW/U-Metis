package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Joe on 11/28/2016.
 */
public class networks {
    private SimpleStringProperty netNetwork;
    private SimpleStringProperty netSubnet;
    private SimpleStringProperty netPort;

    private networks(String netName, String netSubnet, String netPort, String netStatus) {
        this.netNetwork = new SimpleStringProperty(netName);
        this.netSubnet = new SimpleStringProperty(netSubnet);
        this.netPort = new SimpleStringProperty(netPort);
    }

    public String getNetName() {
        return netNetwork.get();
    }
    public void setNetName(String netName) {

        this.netNetwork.set(netName);
    }

    public String getNetSubnet() {

        return netSubnet.get();
    }
    public void setNetSubnet(String netSubnet) {

        this.netSubnet.set(netSubnet);
    }

    public String getNetPort() {

        return netPort.get();
    }
    public void setNetPort(String netPort){

        this.netPort.set(netPort);

    }
}
