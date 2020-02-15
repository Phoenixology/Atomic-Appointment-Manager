package model;

import javafx.collections.ObservableList;

public class City {

    public static int NOID = -1;
    private int cityId;
    private String city;
    private int countryId;



    public City(int cityId, String city, int countryId) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
    }
    public City( String city, int countryId) {
        this.cityId = NOID;
        this.city = city;
        this.countryId = countryId;
    }

        public int getCityId () {
            return cityId;
        }

        public void setCityId ( int cityId){
            this.cityId = cityId;
        }


        public String getCity () {
            return city;
        }

        public void setCity (String city){
            this.city = city;
        }

        public int getCountryId () {
            return countryId;
        }

        public  void  setCountryId ( int countryId){
            this.countryId = countryId;

        }


        @Override
        public String toString () {
            return (Integer.toString(cityId) + " " + city + " " + countryId);
        }
    }

