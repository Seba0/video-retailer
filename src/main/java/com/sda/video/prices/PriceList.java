package com.sda.video.prices;

public enum PriceList {
    NEW(10),
    REGULAR(8),
    PROMO1(7),
    PROMO2(6),
    PROMO3(5),
    PROMO4(3);

    private final int price;
    private PriceList (int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

}
