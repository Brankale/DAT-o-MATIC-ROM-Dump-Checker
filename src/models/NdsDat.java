package models;

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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class NdsDat implements DatFile {

    private final TreeMap<Long, NdsRom> ndsGames;

    public NdsDat(File dat) throws IOException, SAXException, ParserConfigurationException {
        ndsGames = new TreeMap<>();

        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(dat);
        } catch (Exception e) {
            System.out.println("Skip XML validation");
            dbf.setValidating(false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(dat);
        }

        NodeList games = doc.getElementsByTagName("game");

        for (int i = 0; i < games.getLength(); ++i) {
            NdsRom ndsRom = parseNdsGame(games.item(i));
            ndsGames.put(ndsRom.getRomInfo().getCrc32(), ndsRom);
        }
    }

    private NdsRom parseNdsGame(Node node) {
        NdsRom ndsRom = new NdsRom();

        Element element = (Element) node;
        ndsRom.setName(element.getAttribute("name"));

        // TODO: init description

        NodeList roms = element.getElementsByTagName("rom");
        ndsRom.setRomInfo(parseRomInfo(roms.item(0)));

        return ndsRom;
    }

    @Override
    public List<String> acceptedRomExtensions() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("nds");
        return extensions;
    }

    @Override
    public boolean validateCrc(long crc) {
        return ndsGames.containsKey(crc);
    }

    @Override
    public Rom getRomByCrc(long crc) {
        return ndsGames.get(crc);
    }

    private RomInfo parseRomInfo(Node node) {
        RomInfo romInfo = new RomInfo();

        Element element = (Element) node;
        romInfo.setName(element.getAttribute("name"));
        romInfo.setSize(element.getAttribute("size"));
        romInfo.setCrc32(element.getAttribute("crc"));
        romInfo.setMd5(element.getAttribute("md5"));
        romInfo.setSha1(element.getAttribute("sha1"));
        romInfo.setSerial(element.getAttribute("serial"));

        return romInfo;
    }

}
