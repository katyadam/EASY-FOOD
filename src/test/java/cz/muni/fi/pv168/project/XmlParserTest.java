package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.importer.ImporterHandler;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XmlParserTest {

    @Test
    public void shouldParse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        ImporterHandler importerHandler = new ImporterHandler();
        saxParser.parse("src/test/resources/text-import.xml", importerHandler);
        Batch batch = importerHandler.getBatch();
        assert batch.units().size() == 1;
        assert batch.categories().size() == 1;
        assert batch.ingredients().size() == 1;
        assert batch.recipes().size() == 1;
    }

}
