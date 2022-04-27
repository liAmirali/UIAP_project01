import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Medicine {
    private final String name;
    private final String ID;
    private final double price;
    private final LocalDateTime productionDate;
    private final LocalDateTime expirationDate;

    public Medicine(String name, String ID, double price, String productionDate, String expirationDate) {
        this.name = name;
        this.ID = ID;
        this.price = price;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.productionDate = LocalDateTime.parse(productionDate, formatter);
        this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", productionDate=" +  getProductionDate() +
                ", expirationDate=" + getExpirationDate() +
                ", ID='" + getID() + '\'';
    }
}