package com.martink.downloadanddbfill;

import java.io.IOException;

public class ProcessFile {

    public void process() throws IOException {
        InputFile input = new InputFile();

        String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String  saveToPath = "C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources";

        input.downloadFileURL(downloadFromURL, saveToPath);
        input.unzipFile();
    }

}
