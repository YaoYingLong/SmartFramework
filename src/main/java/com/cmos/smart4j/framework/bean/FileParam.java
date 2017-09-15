package com.cmos.smart4j.framework.bean;

import java.io.InputStream;

/**
 * packaging file upload parameters
 */
public class FileParam {

    // upload file name
    private String fileName;
    // upload file field name
    private String fieldName;
    // upload file size
    private long fileSize;
    private String contentType;
    private InputStream inputStream;

    public FileParam(String fileName, String fieldName, long fileSize, String contentType, InputStream inputStream) {
        this.fileName = fileName;
        this.fieldName = fieldName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
