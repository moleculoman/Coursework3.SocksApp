package com.example.socksapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface SocksFileService {

    boolean saveToFile(String json);

    String readFromFile(String s);

    File getDataFile();

    boolean cleanDataFile();

    boolean uploadDataFile(MultipartFile file);
}
