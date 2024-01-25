package com.pmit.ems.api;

import java.io.File;
import java.util.ArrayList;

public class DataPart {
    private String fileName;
    private byte[] content;
    private String type;


    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }

}