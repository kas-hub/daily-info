package com.umarta.dailyinfo.app.xml;

import com.umarta.dailyinfo.app.model.ValCurs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ValCursMarshaller {

    public static final String WINDOWS_1251 = "windows-1251";

    private ValCursMarshaller() {
    }

    public static String marshallValCurs(ValCurs valCurs) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ValCurs.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, WINDOWS_1251);
        StringWriter writer = new StringWriter();
        marshaller.marshal(valCurs, writer);
        return writer.toString();
    }
}
