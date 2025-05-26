package ahmad.model;
import jakarta.persistence.Entity;

@Entity
public class Employee extends Person {
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
