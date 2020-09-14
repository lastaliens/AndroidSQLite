package org.o7planning.sqldemo;

public class Menu {
    int Id;
    String img;
    String name;
    String info;
    int price;
    String content;

    public Menu() {
        super();
    }
    public Menu(int Id, String img, String name, String info, Integer price, String content) {
        super();
        this.Id = Id;
        this.img = img;
        this.name = name;
        this.info = info;
        this.price = price;
        this.content = content;
    }

    public Menu(String name, String info, Integer price, String content) {
    }

    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
