package xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@XmlType(propOrder = {"fields"})
public class XmlRow {
    @XmlTransient
    private Map<String, String> entries = new LinkedHashMap<>();

    public XmlRow() {}

    public XmlRow(Map<String, String> entries) {
        this.entries = entries;
    }

    @XmlElement(name = "field")
    public List<Field> getFields() {
        return entries.entrySet().stream()
                .map(e -> new Field(e.getKey(), e.getValue()))
                .toList();
    }

    public void setFields(List<Field> fields) {
        entries.clear();
        for (Field f : fields) {
            entries.put(f.key, f.value);
        }
    }

    public Map<String, String> toMap() {
        return entries;
    }
}
