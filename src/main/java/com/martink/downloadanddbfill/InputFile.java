package com.martink.downloadanddbfill;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class InputFile {

    private String saveToPathDownload;
    private String fileLocationDownload;
    private String fileLocationUnzip;

    private static String getFilenameFromURL(String downloadFromURL) {
        URL url;
        try {
            url = new URL(downloadFromURL);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return FilenameUtils.getName(url.getPath());
    }

    public void downloadFileURL(String downloadFromURL,
                                String saveToPath) {

        String fileName = getFilenameFromURL(downloadFromURL);
        this.saveToPathDownload = saveToPath;
        this.fileLocationDownload = this.saveToPathDownload + "\\" + fileName;

        try {
            saveFileFromUrlWithCommonsIO(
                    fileLocationDownload, downloadFromURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFileFromUrlWithCommonsIO(String fileLocationDownload,
                                                     String downloadFromURL)
            throws IOException {
        FileUtils.copyURLToFile(new URL(downloadFromURL), new File(fileLocationDownload));
    }

    public void unzipFile() throws IOException {

        File saveToLocationFile = new File(this.saveToPathDownload);
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(this.fileLocationDownload));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            while (zipEntry != null) {
                File newFile = newFile(saveToLocationFile, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    writeFileContent(zis, newFile, buffer);
                }
                zipEntry = zis.getNextEntry();
            }
        }

        zis.closeEntry();
        zis.close();
    }

    private static void writeFileContent(ZipInputStream zis,
                                         File newFile,
                                         byte[] buffer) throws IOException {
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
    }

    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        fileLocationUnzip = destFile.getAbsolutePath();

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public String getFileLocationUnzip() {
        return fileLocationUnzip;
    }
}
