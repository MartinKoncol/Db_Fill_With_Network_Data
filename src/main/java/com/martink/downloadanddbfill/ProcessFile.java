package com.martink.downloadanddbfill;

public class ProcessFile {

    public void process() throws Exception {
        InputFile input = new InputFile();
        DocumentBuilder parsing = new DocumentBuilder();
        JDBC dbConnect = new JDBC();

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String saveToPath = "C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources";

        input.downloadFileURL(downloadFromURL, saveToPath);
        input.unzipFile();

        parsing.dataParsing(input.getFileLocation());
        dbConnect.connection();

        System.out.println("CONNECTED TO DB");

        parsing.nodeListing("vf:Obec");
        parsing.nodeListing("vf:CastObce");

        dbConnect.closeDbConnection();
    }

}
