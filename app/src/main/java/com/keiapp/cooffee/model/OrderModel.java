package com.keiapp.cooffee.model;

import com.google.firebase.firestore.DocumentId;

public class OrderModel {

    @DocumentId
    private String DocId;
    private String name;
    private long price;
    private String description;
    private long discount;

    public OrderModel() {
    }

    public OrderModel(String docId, String name, long price, String description, long discount) {
        DocId = docId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = discount;
    }

    public String getDocId() {
        return DocId;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
