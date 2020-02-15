package model;

public class Country {
    public static int NOID = -1;

    private int countryId;
    private String country;

    public Country(int countryId, String country){

        this.countryId = countryId;
        this.country = country;



    }

    public Country(String country) {
        this.country = country;
        this.countryId = NOID;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    @Override
    public String toString(){
        return(Integer.toString(countryId)+ " " + country);
    }
}
// remove the create and set