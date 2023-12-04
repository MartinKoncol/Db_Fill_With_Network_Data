package com.martink.downloadanddbfill;

import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY_SECTION;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY_SECTION;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;

public class DownloadAndSaveToDB {

    public void downloadAndSaveToDB() throws
            IOException, ParserConfigurationException, SAXException {

        DocumentBuilder parsing = new DocumentBuilder();
        BasicConfigurator.configure();

        SQLiteJDBC dbIns = new SQLiteJDBC();
        dbIns.connectionDB();
        dbIns.createTable(TABLE_MUNICIPALITY);
        dbIns.createTable(TABLE_MUNICIPALITY_SECTION);

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String saveToPath = Paths.get("").toAbsolutePath() + "\\src\\main\\resources";

        InputFile input = new InputFile(downloadFromURL, saveToPath);
        input.downloadFileURL();
        input.unzipFile();

        parsing.dataParsing(input.getFileLocationUnzip());
        parsing.nodeListing(TAG_NAME_MUNICIPALITY, dbIns);
        parsing.nodeListing(TAG_NAME_MUNICIPALITY_SECTION, dbIns);

        dbIns.databaseSelect(TABLE_MUNICIPALITY);
        dbIns.databaseSelect(TABLE_MUNICIPALITY_SECTION);

        dbIns.closeConnectionDB();

    }

}
