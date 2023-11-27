package com.martink.downloadanddbfill;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

public class DownloadAndSaveToDB {

    public void downloadAndSaveToDB() throws
            IOException, SQLException, ParserConfigurationException, SAXException {
        DocumentBuilder parsing = new DocumentBuilder();
        BasicConfigurator.configure();

        JDBC dbIns = new JDBC();
        dbIns.connection();

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String saveToPath = Paths.get("").toAbsolutePath() + "\\src\\main\\resources";

        InputFile input = new InputFile(downloadFromURL, saveToPath);
        input.downloadFileURL();
        input.unzipFile();

        parsing.dataParsing(input.getFileLocationUnzip());
        parsing.nodeListing("vf:Obec", dbIns);
        parsing.nodeListing("vf:CastObce", dbIns);

        dbIns.databaseSelect("Obec");
        dbIns.databaseSelect("Cast_Obce");

        dbIns.closeDbConnection();

    }

}
