package sample;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by equad525 on 12/8/2016.
 */
public class compute
{
    private SimpleStringProperty imgName;
    private SimpleStringProperty imgType;
    private SimpleStringProperty imgStatus;
    private SimpleStringProperty imgPublic;
    private SimpleStringProperty imgProtected;
    private SimpleStringProperty imgFormat;
    private SimpleStringProperty imgSize;

    private compute(String imgName, String imgType, String imgStatus, String imgPublic, String imgProtected, String imgFormat, String imgSize)
    {
        this.imgName = new SimpleStringProperty(imgName);
        this.imgType = new SimpleStringProperty(imgType);
        this.imgStatus = new SimpleStringProperty(imgStatus);
        this.imgPublic = new SimpleStringProperty(imgPublic);
        this.imgProtected = new SimpleStringProperty(imgProtected);
        this.imgFormat = new SimpleStringProperty(imgFormat);
        this.imgSize = new SimpleStringProperty(imgSize);
    }

    public String getImgName() {
        return imgName.get();
    }
    public void setImgName(String netName) { this.imgName.set(netName); }

    public String getImgType() { return imgType.get(); }
    public void setImgType(String imgType) { this.imgType.set(imgType); }

    public String getImgStatus() { return imgStatus.get(); }
    public void setImgStatus(String imgStatus){ this.imgStatus.set(imgStatus); }

    public String getImgPublic() { return imgPublic.get(); }
    public void setImgPublic(String imgPublic) { this.imgPublic.set(imgPublic); }

    public String getImgProtected() { return imgProtected.get(); }
    public void setImgProtected(String imgProtected){ this.imgProtected.set(imgProtected); }

    public String getImgFormat() { return imgFormat.get(); }
    public void setImgFormat(String imgFormat) { this.imgFormat.set(imgFormat); }

    public String getImgSize() { return imgSize.get(); }
    public void setImgSize(String imgSize){ this.imgSize.set(imgSize); }
}
