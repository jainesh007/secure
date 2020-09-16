package com.secure.secure.installer.securelibary.model;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class Data implements  Comparable<Data>{
    int id;
    String title;
    public boolean isSection;

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }

    public Data() {
    }

    public Data(String title,boolean isSection) {
        this.title = title;
        this.isSection = isSection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(@NonNull Data data) {
        String compareQuantity = ((Data) data).getTitle();

        //ascending order
        return this.title.compareTo(data.getTitle());
    }

    public static Comparator<Data> titleNameComparator = new Comparator<Data>() {

        public int compare(Data data1, Data data2) {

            String d1 = data1.getTitle().toUpperCase();
            String d2 = data2.getTitle().toUpperCase();

            //ascending order
            return d1.compareTo(d2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };
}