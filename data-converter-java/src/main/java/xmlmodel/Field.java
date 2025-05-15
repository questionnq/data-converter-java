package xmlmodel;

import jakarta.xml.bind.annotation.XmlType;

@XmlType
public class Field {
    public String key;
    public String value;

    public Field() {}

    public Field(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
