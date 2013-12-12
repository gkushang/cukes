package com.cukesrepo.domain;


import org.springframework.data.mongodb.core.mapping.Field;

import java.io.File;

public class FeatureFile {

    @Field("file")
    private File _file;

    @Field("directoryname")
    private String _directoryName;

    public FeatureFile(File file, String directoryName) {
        _file = file;
        _directoryName = directoryName;
    }

    public File getFile() {
        return _file;
    }

    public void setFile(File file) {
        this._file = file;
    }

    public String getDirectoryName() {
        return _directoryName;
    }

    public void setDirectoryName(String _directoryName) {
        this._directoryName = _directoryName;
    }
}
