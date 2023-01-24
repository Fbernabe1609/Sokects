import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String name;
    private double cost;

    public Product() {
    }

    public Product(int productId, String name, double cost) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}