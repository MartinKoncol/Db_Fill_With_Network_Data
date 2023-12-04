package com.martink.downloadanddbfill;

import static com.martink.downloadanddbfill.Constants.LOGGER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class InputFile {
    private final String downloadFromURL;
    private final String saveToPathDownload;
    private String fileLocationDownload;
    private String fileLocationUnzip;

    public InputFile(String downloadFromURL, String saveToPathDownload) {
        this.downloadFromURL = downloadFromURL;
        this.saveToPathDownload = saveToPathDownload;
    }

    public void downloadFileURL() {
        String fileName = getFilenameFromURL(downloadFromURL);
        this.fileLocationDownload = saveToPathDownload + "\\" + fileName;

        try {
            saveFileFromURL(this.fileLocationDownload, downloadFromURL);
            LOGGER.info("File downloaded from " + downloadFromURL + " to " + fileLocationDownload);
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
    }

    private static void saveFileFromURL(String fileLocationDownload, String downloadFromURL)
            throws IOException {
        FileUtils.copyURLToFile(new URL(downloadFromURL), new File(fileLocationDownload));
    }

    public void unzipFile() throws IOException {

        File saveToLocationFile = new File(this.saveToPathDownload);
        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(this.fileLocationDownload))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                while (zipEntry != null) {
                    File newFile = newFile(saveToLocationFile, zipEntry);
                    if (newFile.exists()) {
                        LOGGER.info("File already unzipped");
                    } else {
                        writeFileContent(zis, newFile, buffer);
                        LOGGER.info("Unzipped file " + fileLocationUnzip);
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
            zis.closeEntry();
        }
    }

    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {

        File destFile = new File(destinationDir, zipEntry.getName());
        fileLocationUnzip = destFile.getAbsolutePath();

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            LOGGER.error("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private static void writeFileContent(ZipInputStream zis, File newFile, byte[] buffer)
            throws IOException {

        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }

    }

    private static String getFilenameFromURL(String downloadFromURL) {
        URL url;
        try {
            url = new URL(downloadFromURL);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return FilenameUtils.getName(url.getPath());
    }

    public String getFileLocationUnzip() {
        return fileLocationUnzip;
    }
}
