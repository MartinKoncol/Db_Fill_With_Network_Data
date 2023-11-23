package com.martink.downloadanddbfill;

public class DownloadAndSaveToDB {

    public void process() throws Exception {
        InputFile input = new InputFile();
        DocumentBuilder parsing = new DocumentBuilder();
        JDBC dbIns = new JDBC ();

        dbIns.connection();

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String saveToPath = "C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources";

        input.downloadFileURL(downloadFromURL, saveToPath);
        input.unzipFile();

        parsing.dataParsing(input.getFileLocation());
        parsing.nodeListing("vf:Obec",dbIns);
        parsing.nodeListing("vf:CastObce",dbIns);

        System.out.println("\nDATABASE SELECT\n");

        dbIns.databaseSelect("Obec");
        dbIns.databaseSelect("Cast_Obce");

        dbIns.closeDbConnection();

    }

}
