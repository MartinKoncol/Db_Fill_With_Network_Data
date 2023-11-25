package com.martink.downloadanddbfill;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.nio.file.Paths;

public class DownloadAndSaveToDB {

    Logger logger = Logger.getLogger(DownloadAndSaveToDB.class);

    public void process() throws Exception {
        DocumentBuilder parsing = new DocumentBuilder();
        BasicConfigurator.configure();

        JDBC dbIns = new JDBC ();
        dbIns.connection();

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String saveToPath = Paths.get("").toAbsolutePath() + "\\src\\main\\resources";

        InputFile input = new InputFile(downloadFromURL,saveToPath);
        input.downloadFileURL();
        input.unzipFile();

        parsing.dataParsing(input.getFileLocationUnzip());
        parsing.nodeListing("vf:Obec",dbIns);
        parsing.nodeListing("vf:CastObce",dbIns);

        logger.info("..::DATABASE SELECT::.. %n".formatted());

        dbIns.databaseSelect("Obec");
        dbIns.databaseSelect("Cast_Obce");

        dbIns.closeDbConnection();

    }

}
