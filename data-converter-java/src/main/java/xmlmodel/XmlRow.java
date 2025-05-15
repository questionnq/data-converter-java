package xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@XmlType(propOrder = {"fields"})
public class XmlRow {

    @XmlElement(name = "field")
    public List<Field> fields = new ArrayList<>();

    @XmlTransient
    public Map<String, String> entries = new LinkedHashMap<>();

    public XmlRow() {}

    public XmlRow(Map<String, String> entries) {
        this.entries = entries;
        this.fields = new ArrayList<>();
        for (Map.Entry<String, String> e : entries.entrySet()) {
            this.fields.add(new Field(e.getKey(), e.getValue()));
        }
    }
}
