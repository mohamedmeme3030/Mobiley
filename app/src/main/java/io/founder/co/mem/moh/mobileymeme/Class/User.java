package io.founder.co.mem.moh.mobileymeme.Class;

public class User {
    private String name,email,password,phone;
    private String imageUri;

    public User() {
    }

    public User(String email , String password, String name, String phone , String imageUri ) {
        this.name =name;
        this.email = email;
        this.password=password;
        this.phone=phone;
        this.imageUri=imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

}
