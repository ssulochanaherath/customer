package lk.ijse.customer.dto.tm;

public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String tele;
    private String email;

    public CustomerTm() {
    }

    public CustomerTm(String id, String name, String address, String tele, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tele = tele;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
