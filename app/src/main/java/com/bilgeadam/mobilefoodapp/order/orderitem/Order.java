package com.bilgeadam.mobilefoodapp.order.orderitem;

import com.bilgeadam.mobilefoodapp.order.shoppingcart.ShoppingCartItem;

import java.util.List;

public class Order {
    private List<ShoppingCartItem> allItems;
    private String userId;
    private String totalPrice;
    public String orderID;
    private String date;
    private String orderIDstr;

    public Order() {
    }

    public Order(List<ShoppingCartItem> allItems, String userId, String totalPrice, String date,String orderIDstr) {
        this.allItems = allItems;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.orderIDstr=orderIDstr;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ShoppingCartItem> getAllItems() {
        return allItems;
    }

    public String getUserId() {
        return userId;
    }


    public void setAllItems(List<ShoppingCartItem> allItems) {
        this.allItems = allItems;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderIDstr() {
        return orderIDstr;
    }

    public void setOrderIDstr(String orderIDstr) {
        this.orderIDstr = orderIDstr;
    }

}
