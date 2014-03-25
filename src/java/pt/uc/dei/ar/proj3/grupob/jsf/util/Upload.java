/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.jsf.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Part;

/**
 * * * @author sofia susana
 */
public class Upload {

    public Upload() {
    }

    public static String handleFileUpload(Part part) throws FileNotFoundException, IOException, FileUploadException{
        String fileName = part.getSubmittedFileName();
        InputStream is = null;
        FileOutputStream fos = null;
        String target = createDir() + fileName;
 
        try {
            is = part.getInputStream();
            File f = new File(target);
            try {
                validateFile(part);
            } catch (FileUploadException ex) {
                throw new FileUploadException(ex.getMessage());
            }
            fos = new FileOutputStream(f);
            byte[] buffer = new byte[4096];//lê ficheiros de 4kb em 4kb            
            int bytesRead;
            while (true) {
                bytesRead = is.read(buffer);
                if (bytesRead > 0) {
                    fos.write(buffer, 0, bytesRead);
                } else {
                    break;
                }
            }
            return target;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage() + " File not found");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw new IOException(e.getMessage() + "File upload failed.");
            }
        }

    }

    public static String createDir() {
        String dirname = "C:\\getPlayWeb\\";
        File d = new File(dirname);
        if (!d.isDirectory()) {
            d.mkdirs();// Create directory now.
            return d.getPath();
        }
        return dirname;
    }

    public static void deleteFile(String filePath) {
        File f = new File(filePath);
        f.delete();
    }

    public static void validateFile(Part value) throws FileUploadException {
        Part file = (Part) value;
        
        if (file.getSize() > 1024000000) {
            throw new FileUploadException("file too big"); 
        }
        
        System.out.println("file: " +file.getContentType().trim());
        
        if (!"audio/mpeg".equalsIgnoreCase(file.getContentType().trim()) 
                && !"audio/mp3".equalsIgnoreCase(file.getContentType().trim())){
            throw new FileUploadException("Not a mp3 file.");
        }
    }
}
