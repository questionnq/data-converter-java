package formats;

import xmlmodel.XmlRow;
import xmlmodel.XmlRowList;
import xmlmodel.Field;
import core.UniversalDataFormat;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class XmlConverter {

    public static UniversalDataFormat read(String inputXmlPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlRowList.class, XmlRow.class, Field.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            XmlRowList rowList = (XmlRowList) unmarshaller.unmarshal(new File(inputXmlPath));
            if (rowList == null) {
                System.out.println("rowList == null");
                return new UniversalDataFormat(List.of());
            }

            if (rowList.getRows() == null || rowList.getRows().isEmpty()) {
                System.out.println("XML не содержит строк (rowList.getRows() пуст).");
                return new UniversalDataFormat(List.of());
            }



            List<Map<String, String>> rows = rowList.getRows().stream()
                    .map(XmlRow::toMap)
                    .collect(Collectors.toList());

            return new UniversalDataFormat(rows);
        } catch (Exception e) {
            System.err.println("Ошибка при чтении XML: ");
            e.printStackTrace();
            return new UniversalDataFormat(List.of());
        }
    }

    public static void write(String outputXmlPath, UniversalDataFormat dataFormat) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlRowList.class, XmlRow.class, Field.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            List<XmlRow> rows = dataFormat.getRows().stream()
                    .map(XmlRow::new)
                    .collect(Collectors.toList());

            XmlRowList rowList = new XmlRowList(rows);
            marshaller.marshal(rowList, new File(outputXmlPath));

            System.out.println("XML-файл успешно записан.");
        } catch (Exception e) {
            System.err.println("Ошибка записи XML: " + e.getMessage());
        }
    }
}