public class Car {
    private String carId;
    private String brand;
    private String model;
    private String status;
    private double price;
    private String type;
    
    public Car(String carId, String brand, String model, String status, double price, String type) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.price = price;
        this.type = type;
    }
    
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}