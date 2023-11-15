package com.martink.downloadanddbfill;

public class ProcessFile {


    String downloadFromURL = "https://www.smartform.cz/download/kopidlno.xml.zip";
    String saveToLocation = "C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources";

    public void process () {
        DownloadURL download = new DownloadURL();

        download.downloadFileURL(downloadFromURL,saveToLocation);
    }

}
