package no.nnsn.ingestor.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileChecker {
    public static byte[] getChecksumBinary(String filePath){
        byte[] checksum = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(filePath)));
            checksum = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checksum;
    }

    public static String getChecksumString(String filepath) {
        return DatatypeConverter.printHexBinary(getChecksumBinary(filepath)).toUpperCase();
    }

    public static String getChecksumString(byte[] binCheck) {
        return DatatypeConverter.printHexBinary(binCheck).toUpperCase();
    }

    public static Boolean fileUnchanged(String path, String dbChecksum) {
        final String fileChecksum = getChecksumString(path);
        return (dbChecksum != null && dbChecksum.equals(fileChecksum)) ? true : false;
    }

}
