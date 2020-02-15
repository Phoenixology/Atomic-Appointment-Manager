package cdisplay;

public class CustomerDisplay {
    private int customerId;
    private int addressId;
    private String customerName;
    private  String address;
    private String phone;
    private String postalCode;


    public CustomerDisplay(int customerId, int addressId, String customerName, String address, String phone, String postalCode ){
        this.customerId = customerId;
        this.addressId = addressId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.postalCode = postalCode;



    }

    public int getCustomerId() {
        return customerId;
    }
    public int getAddressId() { return  addressId; }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
