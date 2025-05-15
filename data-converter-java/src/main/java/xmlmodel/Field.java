package xmlmodel;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
public class Field {
    @XmlAttribute
    public String key;

    @XmlAttribute
    public String value;

    public Field() {}

    public Field(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
