package com.appsaga.lnmiitstationary;

public class Quantity {

    String itemName;
    Boolean decrement;
    String quantity;
    Boolean increment;

    Quantity(String itemName,Boolean decrement,String quantity,Boolean increment){

        this.itemName=itemName;
        this.decrement=decrement;
        this.quantity=quantity;
        this.increment=increment;
    }

    public String getItemName() {
        return itemName;
    }

    public Boolean getDecrement() {
        return decrement;
    }

    public String getQuantity() {
        return quantity;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDecrement(Boolean decrement) {
        this.decrement = decrement;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }
}
