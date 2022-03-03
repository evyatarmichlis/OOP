import oop.ex3.searchengine.*;

import java.util.*;


/**
 * the BoopingClass is a class represent  a
 * new hotel booking site, that allows for personalized search methodologies
 * the user can search the a list of the best hotel by rating,
 * by location and per city
 */
public class BoopingSite {
    /* the consent i use in this class */
    private final float LATITUDE_UPPER_BOUNDS = 90;
    private final float LATITUDE_LOWER_BOUNDS = -90;
    private final float LONGITUDE_UPPER_BOUNDS = 180;
    private final float LONGITUDE_LOWER_BOUNDS = -180;

    /* the hotel list i get from the file the user choose */
    private final Hotel[] hotelsArray;
    /* the hotel name */
    String fileName;


    /** Constructor
      * @param fileName from the file w get all of the hotels
     */
    BoopingSite(String fileName) {
        this.fileName = fileName;
        this.hotelsArray = HotelDataset.getHotels(fileName);

    }

    /**
     * this method get a list of the hotels in the city and rate them by stars
     * if two hotels have the same rate to rate will be by alphabet order
     * @param city the city we want to check
     * @return an array of hotels by rate
     */

    public Hotel[] getHotelsInCityByRating(String city) {
        // create new Arrylist of the hotels in the city, use the helper function
        ArrayList<Hotel> cityHotels = createCityList(city);
        // i create a Comparator to compere base on rate
        HotelsCompereByRate hotelsCompere = new HotelsCompereByRate();
        //sort using the Collection.sort algorithm
        Collections.sort(cityHotels, hotelsCompere);
        Hotel[] finalCityHotels = new Hotel[cityHotels.size()];
        //convert the array list back to Hotel[]
        return cityHotels.toArray(finalCityHotels);

    }

    /**
     *  this method return a sorted list of hotels base on the
     *  proximity to the given location
     * @param latitude;
     * @param longitude;
     * @return a sorted array by proximity
     */

    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        //check if the coordinate are valid
       if (!checkCoordination(latitude, longitude)){
           return new Hotel[]{}; }
        // i create a Comparator to compere base on distance
        HotelDistanceCompere hotelDistanceCompere =
        new HotelDistanceCompere(latitude,longitude);
       //convert the array to arrayList
        List<Hotel> hotels = Arrays.asList(hotelsArray);
        //sort using the Collection.sort algorithm
        Collections.sort(hotels,hotelDistanceCompere);
        //return a array
        Hotel[] finalProximityHotels = new Hotel[hotels.size()];
        return hotels.toArray(finalProximityHotels);

    }

    /**
     *  this method return to the user a list of hotels found in the city and sorted by proximity;
     * @param city the city we want to check
     * @param latitude
     * @param longitude
     * @return sorted list of hotels
     */

    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        //check if the coordinate are valid
        if (!checkCoordination(latitude, longitude)){
            return new Hotel[]{}; }
        // create new Arrylist of the hotels in the city, use the helper function
        ArrayList<Hotel> cityHotels = createCityList(city);
        // i create a Comparator to compere base on distance
        HotelDistanceCompere hotelDistanceCompere = new HotelDistanceCompere(latitude,longitude);
        //sort using the Collection.sort algorithm
        Collections.sort(cityHotels,hotelDistanceCompere);
        Hotel[] finalProximityCityHotels = new Hotel[cityHotels.size()];
        return cityHotels.toArray(finalProximityCityHotels);

    }
    /* helper function , use to find all the hotels in the current city
     */
    private ArrayList<Hotel> createCityList(String city){
        ArrayList<Hotel> cityHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotelsArray) {
            if (hotel.getCity().equals(city)) {
                cityHotels.add(hotel);
            }

        }
        return cityHotels;
    /*
    helper method , check if the given coordination is valid .
     */
    }
    private boolean checkCoordination( double latitude, double longitude ){
        if (latitude > LATITUDE_UPPER_BOUNDS || latitude < LATITUDE_LOWER_BOUNDS
                || longitude > LONGITUDE_UPPER_BOUNDS || longitude < LONGITUDE_LOWER_BOUNDS)
        {
            return false;
        }
        return  true;

    }




    }

