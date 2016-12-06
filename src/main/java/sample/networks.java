package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Joe on 11/28/2016.
 */
public class networks {
    private SimpleStringProperty netName;
    private SimpleStringProperty netSubnet;
    private SimpleStringProperty netPort;
    private SimpleStringProperty netStatus;

    private networks(String netName, String netSubnet, String netPort, String netStatus) {
        this.netName = new SimpleStringProperty(netName);
        this.netSubnet = new SimpleStringProperty(netSubnet);
        this.netPort = new SimpleStringProperty(netPort);
        this.netStatus = new SimpleStringProperty(netStatus);
    }

    public String getNetName() {
        return netName.get();
    }
    public void setNetName(String netName) {

        this.netName.set(netName);
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
