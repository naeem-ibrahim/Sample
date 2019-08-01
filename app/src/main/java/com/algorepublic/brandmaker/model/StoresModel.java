package com.algorepublic.brandmaker.model;

import com.google.gson.annotations.SerializedName;

public class StoresModel {

    @SerializedName("id")
    private int id;
    @SerializedName("spar_store_id")
    private String sparStoreId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("address")
    private Object address;
    @SerializedName("store_format")
    private String storeFormat;
    @SerializedName("store_cluster")
    private int storeCluster;
    @SerializedName("store_location")
    private String storeLocation;
    @SerializedName("store_region")
    private String storeRegion;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;


    public int getId() {
        return id;
    }

    public String getSparStoreId() {
        return sparStoreId;
    }

    public String getStoreName() {
        return storeName;
    }

    public Object getAddress() {
        return address;
    }

    public String getStoreFormat() {
        return storeFormat;
    }

    public int getStoreCluster() {
        return storeCluster;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getStoreRegion() {
        return storeRegion;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
