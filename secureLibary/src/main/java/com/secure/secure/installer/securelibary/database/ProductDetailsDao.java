package com.secure.secure.installer.securelibary.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.List;

@Dao
public interface ProductDetailsDao {

    // get row count from database
    @Query("SELECT COUNT(*) FROM " + GlobalClass.TABLE_NAME)
    int getCount();

    // get row count from database
    @Query("SELECT " + GlobalClass.Version + " FROM " + GlobalClass.TABLE_NAME + " LIMIT  1")
    int getVersion();

    // delete all data from database
    @Query("DELETE FROM " + GlobalClass.TABLE_NAME)
    void deleteAllData();

    // insert data to database
    @Insert
    void InsertProduct(ProductTableData productDetails);


    // get all manufacturerName
    @Query("SELECT distinct " + GlobalClass.ManufacturerName + " FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + " != '" + GlobalClass.COMPANY_NAME + "'")
    List<String> getAllManufacturerName();


    // get all product name according manufacturerName
    @Query("SELECT distinct " + GlobalClass.ProductModelName + " FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + "= :productName")
    List<String> getAllProductName(String productName);

    // get product image according product name
    @Query("SELECT " + GlobalClass.Image + " FROM " + GlobalClass.TABLE_NAME + " where lower(" + GlobalClass.ProductModelName + ") Like '%' || :productName || '%'")
    String getProductImage(String productName);


    // get all other compatible product according manufacturerName and product name
    @Query("SELECT distinct " + GlobalClass.OtherCompatibleProductName + " FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + "= :manufacturerName and " + GlobalClass.ProductModelName + "=:productName")
    List<String> getAllCompatibleProduct(String manufacturerName, String productName);


    // get all other Recommended product according manufacturerName and product name
    @Query("SELECT distinct " + GlobalClass.RecommendProductName + " FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + "= :manufacturerName and " + GlobalClass.ProductModelName + "=:productName")
    List<String> getAllRecommendedProduct(String manufacturerName, String productName);


    // get application details according product name
    @Query("SELECT " + GlobalClass.ApplicationDetails + " FROM " + GlobalClass.TABLE_NAME + " where lower(" + GlobalClass.ProductModelName + ") Like '%' || :productName || '%'")
    String getApplicationDetails(String productName);


    // get data of secure or bottom wiring diagram product
    @Query("SELECT * FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + "= '" + GlobalClass.COMPANY_NAME
            + "' and lower(" + GlobalClass.ProductModelName + ")" + " LIKE '%' || :productName || '%'")
    List<ProductTableData> getBottomWiringDiagram(String productName);


    // get data of upper wiring diagram RecommendProductName product
    @Query("SELECT * FROM " + GlobalClass.TABLE_NAME
            + " where lower(" + GlobalClass.ManufacturerName + ") = :manufacturerName "
            + "and lower(" + GlobalClass.ProductModelName + ") " + " LIKE '%' || :productName || '%'"
            + "and lower(" + GlobalClass.RecommendProductName + ") Like '%' || :replace_product || '%'")
    List<ProductTableData> getUpperRecommendProductNameWiringDiagram(String manufacturerName, String productName, String replace_product);


    // get data of upper wiring diagram OtherCompatibleProductName product
    @Query("SELECT * FROM " + GlobalClass.TABLE_NAME
            + " where lower(" + GlobalClass.ManufacturerName + ") = :manufacturerName "
            + "and lower(" + GlobalClass.ProductModelName + ") " + " LIKE '%' || :productName || '%'"
            + "and lower(" + GlobalClass.OtherCompatibleProductName + ") LIKE '%' || :replace_product || '%'")
    List<ProductTableData> getUpperOtherCompatibleWiringDiagram(String manufacturerName, String productName, String replace_product);


    // get all product of secure
    @Query("SELECT *  FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + " = '" + GlobalClass.COMPANY_NAME + "'")
    List<ProductTableData> getAllProductOfSecure();

    // getSecureProduct
    @Query("SELECT *  FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + " = '" + GlobalClass.COMPANY_NAME + "'"
            + "and lower(" + GlobalClass.ProductModelName + ") " + " LIKE '%' || :productName || '%'")
    List<ProductTableData> getSecureProduct(String productName);

    // update the search count
    @Query("UPDATE " + GlobalClass.TABLE_NAME + " SET " + GlobalClass.SEARCH_COUNT + " = :count WHERE " + GlobalClass.ManufacturerName + " = '" + GlobalClass.COMPANY_NAME + "'"
            + "and lower(" + GlobalClass.ProductModelName + ") " + " LIKE '%' || :productName || '%'")
    int updateSearchCount(int count, String productName);

    // get the search count
    @Query("SELECT  " + GlobalClass.SEARCH_COUNT + "  FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + " = '" + GlobalClass.COMPANY_NAME + "'"
            + "and lower(" + GlobalClass.ProductModelName + ") " + " LIKE '%' || :productName || '%'")
    int getSearchCount(String productName);


    // get the search count
    @Query("SELECT  * FROM " + GlobalClass.TABLE_NAME + " where " + GlobalClass.ManufacturerName + " = '" + GlobalClass.COMPANY_NAME + "'"
            + "and " + GlobalClass.SEARCH_COUNT + " != 0 ORDER BY "+ GlobalClass.SEARCH_COUNT +  " DESC " )
    List<ProductTableData> getSearchCountProduct();



}


