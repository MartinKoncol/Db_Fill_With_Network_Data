package com.martink.downloadanddbfill;

public class ProcessFile {

    public void process() throws Exception {

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String  saveToPath = "C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources";

        InputFile input = new InputFile();

        input.downloadFileURL(downloadFromURL, saveToPath);
        input.unzipFile();

        DocumentBuilder parsing = new DocumentBuilder();
        parsing.DataParsing(input.getFileLocation());
    }

}
