package core;

import java.util.List;
import java.util.Map;

public class UniversalDataFormat {

    /*
     данные — список строк, где каждая строка — это map "заголовок - значение".
      например: [ {"id": "1", "name": "Alice"}, ... ]
     */

    private final List<Map<String, String>> rows;

    public UniversalDataFormat(List<Map<String, String>> rows) {
        this.rows = rows;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }
}
