package com.martink.downloadanddbfill;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import static java.lang.System.out;

public class DownloadURL {

    private String saveToLocation;
    private String downloadFromURL;
    private String fileName;


    public void downloadFileURL(String downloadFromURL, String saveToLocation) {

        this.downloadFromURL = downloadFromURL;
        this.saveToLocation = saveToLocation;

        fileName = getFilenameFromURL(this.downloadFromURL);
        try {
            saveFileFromUrlWithCommonsIO(
                    this.saveToLocation + "//" + this.fileName, downloadFromURL);
            out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileFromUrlWithCommonsIO(String fileName,
                                                    String fileUrl)
            throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }

    public static String getFilenameFromURL(String downloadFromURL) {
        URL url = null;
        try {
            url = new URL(downloadFromURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return FilenameUtils.getName(url.getPath());
    }

}
