package xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rows")
public class XmlRowList {
    private List<XmlRow> rows;

    public XmlRowList() {}

    public XmlRowList(List<XmlRow> rows) {
        this.rows = rows;
    }

    @XmlElement(name = "row")
    public List<XmlRow> getRows() {
        return rows;
    }

    public void setRows(List<XmlRow> rows) {
        this.rows = rows;
    }
}