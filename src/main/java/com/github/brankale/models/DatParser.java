package com.github.brankale.models;

import com.github.brankale.models.dat.DatEntry;
import com.github.brankale.models.dat.DatFile;
import com.github.brankale.models.dat.Rom;
import com.github.brankale.models.dat.RomStatus;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DatParser {

    private DatParser() {
        // avoid instantiation
    }

    public static DatFile parse(File dat) throws ParserConfigurationException, IOException, SAXException {
        DatFile datFile = new DatFile();

        Document doc = getDocument(dat);
        List<DatEntry> datEntries = parseDatEntries(doc);

        for (DatEntry datEntry : datEntries)
            datFile.addEntry(datEntry);

        return datFile;
    }

    private static Document getDocument(File dat) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // skip xml validation
        dbf.setValidating(false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(dat);
    }

    private static List<DatEntry> parseDatEntries(Document document) {
        List<DatEntry> datEntries = new LinkedList<>();

        NodeList entries = document.getElementsByTagName("game");
        for (int i = 0; i < entries.getLength(); ++i) {
            DatEntry datEntry = parseDatEntry(entries.item(i));
            datEntries.add(datEntry);
        }

        return datEntries;
    }

    private static DatEntry parseDatEntry(Node node) {
        Element element = (Element) node;

        String name = element.getAttribute("name");
        Node romNode = element.getElementsByTagName("rom").item(0);
        Rom rom = parseRom(romNode);

        return new DatEntry(name, rom);
    }

    private static Rom parseRom(Node node) {
        Element element = (Element) node;
        String name = element.getAttribute("name");
        String crc = element.getAttribute("crc");
        String md5 = element.getAttribute("md5");
        String sha1 = element.getAttribute("sha1");
        String sha256 = element.getAttribute("sha256");
        String size = element.getAttribute("size");
        String serial = element.getAttribute("serial");
        String status = element.getAttribute("status");

        Rom.Builder builder = new Rom.Builder();
        builder.setName(StringEscapeUtils.unescapeXml(name));
        builder.setSize(Long.parseLong(size));
        builder.setCrc(Long.decode("0x" + crc));
        if (!md5.isEmpty()) builder.setMd5(md5);
        if (!sha1.isEmpty()) builder.setSha1(sha1);
        if (!sha256.isEmpty()) builder.setSha256(sha256);
        if (!serial.isEmpty()) builder.setSerial(serial);
        builder.setStatus(RomStatus.parse(status));

        return builder.build();
    }

}
