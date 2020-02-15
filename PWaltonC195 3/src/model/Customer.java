package model;

public class Customer {
    private int customerId;

    private String customerName;
    private int addressId;
    private int active;
    public final static int NOID = -1;



    public Customer(int customerId,  String customerName, int addressId, int active) {
        this.customerId = customerId;

        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;



    }

    public Customer( String customerName, int addressId, int active) {
        this.customerId = NOID;

        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;



    }
    public int getCustomerId() {
        return customerId;
    }



    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return(customerName );
    }

}
