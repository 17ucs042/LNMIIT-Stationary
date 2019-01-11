package com.appsaga.lnmiitstationary;

public class Items {

    int ID;
    String name;
    boolean perishable;
    int quantity;
    int OnHold;
    int minQty;
    boolean selected;

    Items(int ID,String name,boolean perishable,int quantity,int onHold,int minQty,boolean selected){

        this.ID=ID;
        this.name=name;
        this.perishable=perishable;
        this.quantity=quantity;
        this.OnHold=onHold;
        this.minQty=minQty;
        this.selected=selected;
    }

    String getID(){
        return ID+"";
    }

    public String getName() {
        return name;
    }

    public String isPerishable() {
        if(perishable)
            return "Yes";
        else
            return "No";
    }

    public String getQuantity() {
        return quantity+"";
    }

    public String getOnHold() {
        return OnHold+"";
    }

    public String getMinQty() {
        return minQty+"";
    }

    boolean isSelected(){

        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
