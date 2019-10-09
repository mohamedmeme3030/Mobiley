package io.founder.co.mem.moh.mobileymeme.Class;
import java.io.Serializable;

public class Mobile implements Serializable {
    private String image;
    private String imageTow;
    private String imageThree;
    private String brand;
    private String model;
    private String color;
    private String price;
    private String phone;
    private String detail;
    private String condition;
    private String location;
    private String name;
    private String storageCapacity;
    private String currentDate;
    private String ID;
  public Mobile(String image, String imageTow, String brand, String model, String color, String price, String phone, String detail, String condition, String location, String name) {
        this.image = image;
        this.imageTow = imageTow;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.phone = phone;
        this.detail = detail;
        this.condition = condition;
        this.location = location;
        this.name = name;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public Mobile() {

    }
    public String getImageTow() {
        return imageTow;
    }

    public void setImageTow(String imageTow) {
        this.imageTow = imageTow;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
