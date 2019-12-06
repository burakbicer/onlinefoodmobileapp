package com.bilgeadam.mobilefoodapp.order.shoppingcart;

// class for shoppingCart items

public class ShoppingCartItem {
    public String itemDatabaseKey;
    private String itemId;
    private String quantity;
    private String name;
    private String price;

    public ShoppingCartItem(String name, String price, String itemId, String quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public ShoppingCartItem() {
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {

        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }
}

