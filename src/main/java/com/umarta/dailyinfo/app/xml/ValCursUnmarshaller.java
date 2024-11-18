package com.umarta.dailyinfo.app.xml;

import com.umarta.dailyinfo.app.model.ValCurs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public final class ValCursUnmarshaller {

    private ValCursUnmarshaller() {
    }

    public static ValCurs unmarshallValCurs(String xmlAsString) throws JAXBException {
        StringReader reader = new StringReader(xmlAsString);
        JAXBContext context = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ValCurs) unmarshaller.unmarshal(reader);
    }
}
