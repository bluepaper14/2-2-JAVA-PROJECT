package project;

public class Product {
    private String imagePath;  // 이미지 경로
    private String description;  // 제품 설명
    private String price;  // 가격

    public Product(String imagePath, String description, String price) {
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
