package ahmad.hodel;
import jakarta.persistence.Entity;

@Entity
public class Customer extends Person {
    private String loyaltyCardNumber;

    public String getLoyaltyCardNumber() {
        return loyaltyCardNumber;
    }

    public void setLoyaltyCardNumber(String loyaltyCardNumber) {
        this.loyaltyCardNumber = loyaltyCardNumber;
    }
}
