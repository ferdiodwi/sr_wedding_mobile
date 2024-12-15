//package com.example.mos_innovative;
//
//public class SpinnerItem {
//    private String name;
//    private double cost;
//    private int imageResId; // Resource ID for the image
//
//    // Constructor
//    public SpinnerItem(String name, double cost, int imageResId) {
//        this.name = name;
//        this.cost = cost;
//        this.imageResId = imageResId;
//    }
//
//    // Getter for name
//    public String getName() {
//        return name;
//    }
//
//    // Getter for cost
//    public double getCost() {
//        return cost;
//    }
//
//    // Getter for image resource ID
//    public int getImageResId() {
//        return imageResId;
//    }
//
//    @Override
//    public String toString() {
//        return name; // This will be shown in the ListView
//    }
//}


package com.example.sr_wedding;

public class SpinnerItem {
    private String name;
    private int cost;
    private int imageResId;

    // Pastikan constructor dan getter/setter bekerja dengan benar
    public SpinnerItem(String name, int cost, int imageResId) {
        this.name = name;
        this.cost = cost;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getImageResId() {
        return imageResId;
    }
}
