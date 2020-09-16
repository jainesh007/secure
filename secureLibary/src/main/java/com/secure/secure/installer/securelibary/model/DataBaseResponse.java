package com.secure.secure.installer.securelibary.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataBaseResponse {

    @SerializedName("dbTableVersion")
    private int dbTableVersion;

    @SerializedName("responseCode")
    private int responseCode;

    @SerializedName("recordCount")
    private int recordCount;

    @SerializedName("responseObj")
    private ArrayList<DataBaseModelResponse> response;


    public int getDbTableVersion() {
        return dbTableVersion;
    }

    public void setDbTableVersion(int dbTableVersion) {
        this.dbTableVersion = dbTableVersion;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public ArrayList<DataBaseModelResponse> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<DataBaseModelResponse> response) {
        this.response = response;
    }
}
