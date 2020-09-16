package com.secure.secure.installer.securelibary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.secure.secure.installer.securelibary.database.GlobalClass;

@Entity(tableName = GlobalClass.TABLE_NAME)
public class ProductTableData {

    @PrimaryKey(autoGenerate = true)
    int product_id;

    @ColumnInfo(name = GlobalClass.ID)
    private int id;

    @ColumnInfo(name = GlobalClass.ManufacturerName)
    private String manufacturerName;

    @ColumnInfo(name = GlobalClass.ProductModelName)
    private String productModelName;

    @ColumnInfo(name = GlobalClass.Pair1)
    private String pair1;

    @ColumnInfo(name = GlobalClass.Pair2)
    private String pair2;

    @ColumnInfo(name = GlobalClass.Pair3)
    private String pair3;

    @ColumnInfo(name = GlobalClass.Pair4)
    private String pair4;

    @ColumnInfo(name = GlobalClass.Pair5)
    private String pair5;

    @ColumnInfo(name = GlobalClass.Pair6)
    private String pair6;

    @ColumnInfo(name = GlobalClass.Pair7)
    private String pair7;

    @ColumnInfo(name = GlobalClass.Pair8)
    private String pair8;

    @ColumnInfo(name = GlobalClass.Pair9)
    private String pair9;

    @ColumnInfo(name = GlobalClass.Pair10)
    private String pair10;

    @ColumnInfo(name = GlobalClass.Pair11)
    private String pair11;

    @ColumnInfo(name = GlobalClass.Pair12)
    private String pair12;

    @ColumnInfo(name = GlobalClass.PairSpare)
    private String pairSpare;


    @ColumnInfo(name = GlobalClass.OtherCompatibleProductName)
    private String otherCompatibleProductName;

    @ColumnInfo(name = GlobalClass.RecommendProductName)
    private String recommendProductName;

    @ColumnInfo(name = GlobalClass.RecommendProductInstructions)
    private String recommendProductInstructions;

    @ColumnInfo(name = GlobalClass.ProductCategory)
    private String productCategory;

    @ColumnInfo(name = GlobalClass.ProductSubCategory)
    private String productSubCategory;

    @ColumnInfo(name = GlobalClass.ModelImagePath)
    private String modelImagePath;

    @ColumnInfo(name = GlobalClass.ApplicationDetails)
    private String applicationDetails;

    @ColumnInfo(name = GlobalClass.Image)
    private String image;

    @ColumnInfo(name = GlobalClass.WiringAndBackPlate)
    private String wiringAndBackPlate;

    @ColumnInfo(name = GlobalClass.Instructions)
    private String instructions;

    @ColumnInfo(name = GlobalClass.IsCommissioningProduct)
    private String isCommissioningProduct;

    @ColumnInfo(name = GlobalClass.FAQ_URL)
    private String faqurl;

    @ColumnInfo(name = GlobalClass.VIDEO_URL)
    private String videoURL;

    @ColumnInfo(name = GlobalClass.OTHER_URL)
    private String otherUrl;

    @ColumnInfo(name = GlobalClass.IS_NEW_PRODUCT)
    private String is_new_product;

    @ColumnInfo(name = GlobalClass.Version)
    private int version;

    @ColumnInfo(name = GlobalClass.SEARCH_COUNT)
    private int search_count;

    @ColumnInfo(name = GlobalClass.IS_Active)
    private boolean isActive;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductModelName() {
        return productModelName;
    }

    public void setProductModelName(String productModelName) {
        this.productModelName = productModelName;
    }

    public String getPair1() {
        return pair1;
    }

    public void setPair1(String pair1) {
        this.pair1 = pair1;
    }

    public String getPair2() {
        return pair2;
    }

    public void setPair2(String pair2) {
        this.pair2 = pair2;
    }

    public String getPair3() {
        return pair3;
    }

    public void setPair3(String pair3) {
        this.pair3 = pair3;
    }

    public String getPair4() {
        return pair4;
    }

    public void setPair4(String pair4) {
        this.pair4 = pair4;
    }

    public String getPair5() {
        return pair5;
    }

    public void setPair5(String pair5) {
        this.pair5 = pair5;
    }

    public String getPair6() {
        return pair6;
    }

    public void setPair6(String pair6) {
        this.pair6 = pair6;
    }

    public String getPair7() {
        return pair7;
    }

    public void setPair7(String pair7) {
        this.pair7 = pair7;
    }

    public String getPair8() {
        return pair8;
    }

    public void setPair8(String pair8) {
        this.pair8 = pair8;
    }

    public String getPair9() {
        return pair9;
    }

    public void setPair9(String pair9) {
        this.pair9 = pair9;
    }

    public String getPair10() {
        return pair10;
    }

    public void setPair10(String pair10) {
        this.pair10 = pair10;
    }

    public String getOtherCompatibleProductName() {
        return otherCompatibleProductName;
    }

    public void setOtherCompatibleProductName(String otherCompatibleProductName) {
        this.otherCompatibleProductName = otherCompatibleProductName;
    }

    public String getRecommendProductName() {
        return recommendProductName;
    }

    public void setRecommendProductName(String recommendProductName) {
        this.recommendProductName = recommendProductName;
    }

    public String getRecommendProductInstructions() {
        return recommendProductInstructions;
    }

    public void setRecommendProductInstructions(String recommendProductInstructions) {
        this.recommendProductInstructions = recommendProductInstructions;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getModelImagePath() {
        return modelImagePath;
    }

    public void setModelImagePath(String modelImagePath) {
        this.modelImagePath = modelImagePath;
    }

    public String getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(String applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWiringAndBackPlate() {
        return wiringAndBackPlate;
    }

    public void setWiringAndBackPlate(String wiringAndBackPlate) {
        this.wiringAndBackPlate = wiringAndBackPlate;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getFaqurl() {
        return faqurl;
    }

    public void setFaqurl(String faqurl) {
        this.faqurl = faqurl;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getIsCommissioningProduct() {
        return isCommissioningProduct;
    }

    public void setIsCommissioningProduct(String isCommissioningProduct) {
        this.isCommissioningProduct = isCommissioningProduct;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public String getIs_new_product() {
        return is_new_product;
    }

    public void setIs_new_product(String is_new_product) {
        this.is_new_product = is_new_product;
    }

    public int getSearch_count() {
        return search_count;
    }

    public void setSearch_count(int search_count) {
        this.search_count = search_count;
    }


    public String getPair11() {
        return pair11;
    }

    public void setPair11(String pair11) {
        this.pair11 = pair11;
    }

    public String getPair12() {
        return pair12;
    }

    public void setPair12(String pair12) {
        this.pair12 = pair12;
    }

    public String getPairSpare() {
        return pairSpare;
    }

    public void setPairSpare(String pairSpare) {
        this.pairSpare = pairSpare;
    }
}
