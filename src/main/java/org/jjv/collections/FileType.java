package org.jjv.collections;

public enum FileType {
    PDF("pdf"),
    XLS("xls"),
    XLSX("xlsx");

    private String extension;

    FileType(String extension){
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
