package model;

public class Product {
    private int code;
    private String name;
    private int amount;
    private int price;
    private int supplier;

    public Product(int code, String name, int amount, int price, int supplier) {
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.supplier = supplier;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }
}
