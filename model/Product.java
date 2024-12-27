package model;

public class Product {
    private int p_code;
    private String p_name;
    private int p_amount;
    private int p_price;
    private int supplier;

    public Product(int code, String name, int amount, int price, int supplier) {
        this.p_code = code;
        this.p_name = name;
        this.p_amount = amount;
        this.p_price = price;
        this.supplier = supplier;
    }

    public int getCode() {
        return p_code;
    }

    public void setCode(int code) {
        this.p_code = code;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getP_amount() {
        return p_amount;
    }

    public void setP_amount(int p_amount) {
        this.p_amount = p_amount;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }

    public String[] toStringArray() {
        return new String[] {Integer.toString(p_code), p_name, Integer.toString(p_amount), Integer.toString(p_price), Integer.toString(supplier)};
    }
}
