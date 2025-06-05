package com.example.demo.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull
    @NotEmpty(message = "tên sản phẩm không được để trống")
   private String name;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "price phải lớn hơn 0")
   private double price;

   private String image;

    @NotNull
    @NotEmpty(message = "detailDesc không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    @NotNull
    @NotEmpty(message = "shortDesc không được để trống")
   private String ShortDesc;

    @NotNull
    @Min(value = 1, message = "Số lượng cần lớn hơn hoặc bằng 1")
   private String quantity;


   private long sold;
   private String factory;
   private String target;



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public String getShortDesc() {
        return ShortDesc;
    }

    public long getSold() {
        return sold;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public void setShortDesc(String shortDesc) {
        ShortDesc = shortDesc;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", detailDesc='" + detailDesc + '\'' +
                ", ShortDesc='" + ShortDesc + '\'' +
                ", quantity='" + quantity + '\'' +
                ", sold=" + sold +
                ", factory='" + factory + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
