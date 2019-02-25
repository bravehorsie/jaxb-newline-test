import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringReader;
import java.io.StringWriter;

public class NewLineTest {

    private static final String VALUE = "[CRLF]:\r\n[CR]:\r[LF]:\nf";
    private Bean bean;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    @Before
    public void setUp() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Bean.class);
        marshaller = ctx.createMarshaller();
        unmarshaller = ctx.createUnmarshaller();

        bean = new Bean();
        bean.setAttributeString(VALUE);
        bean.setElementString(VALUE);
    }

    @Test
    public void testNewLines() throws JAXBException {
        StringWriter writer = new StringWriter();
        marshaller.marshal(bean, writer);
        String XML = writer.toString().replaceAll("\r", "CR").replaceAll("\n", "LF");
        System.out.println("XML = " + XML);

        Bean reuslt = (Bean) unmarshaller.unmarshal(new StringReader(XML));
        System.out.println("reuslt.getElementString().replaceAll(\"\\r\", \"CR\") = " + reuslt.getElementString().replaceAll("\r", "CR").replaceAll("\n", "LF"));
        System.out.println("reuslt.getAttributeString().replaceAll(\"\\r\", \"CR\") = " + reuslt.getAttributeString().replaceAll("\r", "CR").replaceAll("\n", "LF"));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"fElementString", "fAttributeString"})
    @XmlRootElement(name = "Bean")
    public static class Bean {

        @XmlElement(name = "ElementString")
        protected String fElementString;

        @XmlAttribute(name = "AttributeString")
        protected String fAttributeString;

        public String getElementString() {
            return fElementString;
        }

        public void setElementString(String elementString) {
            fElementString = elementString;
        }

        public String getAttributeString() {
            return fAttributeString;
        }

        public void setAttributeString(String attributeString) {
            fAttributeString = attributeString;
        }

    }
}

