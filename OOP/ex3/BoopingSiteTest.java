
import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class BoopingSiteTest {
    /*create empty list for the test*/
    Hotel[] emptyarray = new Hotel[]{};
    /*create booping site using the text we got */
    BoopingSite boopingSite1 = new BoopingSite("hotels_dataset.txt");
    BoopingSite boopingSite2 = new BoopingSite("hotels_tst1.txt");
    BoopingSite emptyBoopingSite = new BoopingSite("hotels_tst2.txt");

    /*
     check the number of cities
     */
    private ArrayList<Hotel> createCityList(Hotel[] hotelList, String city) {
        ArrayList<Hotel> cityHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotelList) {
            if (hotel.getCity().equals(city)) {
                cityHotels.add(hotel);
            }

        }
        return cityHotels;
    }

    /**
     * test for the getHotelsInCity
     */

    @Test
    public void getHotelsInCityByRatingTest() {
        Hotel[] cityArray1 = boopingSite1.getHotelsInCityByRating("manali");
        int diffStarRate = 100;
        String diffName = "";
        for (Hotel hotel : cityArray1) {
            System.out.println("name :" + hotel.getPropertyName() + "city: " + hotel.getCity() + " number of stars " + hotel.getStarRating());
            assertTrue((hotel.getCity().equals("manali"))
                    && (hotel.getStarRating() < diffStarRate || hotel.getStarRating() == diffStarRate && hotel.getPropertyName().compareTo(diffName) >= 0));

            diffName = hotel.getPropertyName();
            diffStarRate = hotel.getStarRating();

        }


        // try to get a city when the text is empty
        Hotel[] cityArray2 = emptyBoopingSite.getHotelsInCityByRating("manali");
        assertArrayEquals(cityArray2, emptyarray);

        //try to get a city when the city dont exist
        Hotel[] cityArray3 = boopingSite1.getHotelsInCityByRating("petah tikva");
        assertArrayEquals(cityArray3, emptyarray);

        //empty city string
        Hotel[] cityArray4 = boopingSite1.getHotelsInCityByRating("");
        assertArrayEquals(cityArray4, emptyarray);

        //check if the size of the city i get from the txt is == to
        // the number of city i get from the method

        Hotel[] cityArray5 = boopingSite2.getHotelsInCityByRating("manali");
        ArrayList<Hotel> cityHotels = (createCityList(HotelDataset.getHotels("hotels_dataset.txt"), "manali"));
        Hotel[] finalCityHotels = new Hotel[cityHotels.size()];
        cityHotels.toArray(finalCityHotels);

        assertEquals(finalCityHotels.length, cityArray5.length);


    }

    /**
     * test the getHotelsByProximity method
     */
    @Test
    public void getHotelsByProximityTest() {

        //check if our method work
        double prevDistance = -1;
        int numberOfPoi = 0;
        Hotel[] proxArray1 = boopingSite2.getHotelsByProximity(32.25328551, 77.17982834);
        for (Hotel hotel : proxArray1) {

            double distance = Math.sqrt((Math.pow(hotel.getLatitude() - 32.25328551, 2))
                    + Math.pow((hotel.getLongitude() - 77.17982834), 2));
            System.out.println("distance " + distance + " number of POI" + hotel.getNumPOI());
            assertTrue(distance > prevDistance
            || (distance == prevDistance && hotel.getNumPOI() <= numberOfPoi));
            numberOfPoi = hotel.getNumPOI();
            prevDistance = distance;

        }
        // try tp enter invalid Coordinate
        Hotel[] proxArray2 = boopingSite2.getHotelsByProximity(200, 77.17982834);
        assertArrayEquals(emptyarray, proxArray2);

        Hotel[] proxArray3 = boopingSite2.getHotelsByProximity(150, -500);
        assertArrayEquals(emptyarray, proxArray2);

        // try to equal valid  coordinates with empty data base
        Hotel[] proxArray4 = emptyBoopingSite.getHotelsByProximity(150, 45);
        assertArrayEquals(emptyarray, proxArray2);


    }

    /**
     * test the getHotelsCityByProximity method
     */

    @Test
    public void getHotelsInCityByProximityTest() {
        // test the main method . check if the list we get is the list we want
        double prevDistance = -1;
        int numberOfPoi = 0;
        Hotel[] proxCityArray1 = boopingSite1.getHotelsInCityByProximity("delhi", 32.25328551, 77.17982834);
        for (Hotel hotel : proxCityArray1) {

            double distance = Math.sqrt((Math.pow(hotel.getLatitude() - 32.25328551, 2))
                + Math.pow((hotel.getLongitude() - 77.17982834), 2));
            System.out.println("city: " + hotel.getCity()
            + "distance " + distance + " number of POI" + hotel.getNumPOI());
            assertTrue(hotel.getCity().equals("delhi")
            && distance > prevDistance ||
            (distance == prevDistance && hotel.getNumPOI() <= numberOfPoi));
            numberOfPoi = hotel.getNumPOI();
            prevDistance = distance;


        }

        //check if we put invalid input
        Hotel[] proxCityArray2 = boopingSite2.getHotelsInCityByProximity("delhi",20000,80);
        assertArrayEquals(emptyarray, proxCityArray2);
        //check if we put invalid input
        Hotel[] proxCityArray3 = boopingSite2.getHotelsInCityByProximity("delhi",20,-850);
        assertArrayEquals(emptyarray, proxCityArray3);
        // invalid city
        Hotel[] proxCityArray4 = boopingSite2.getHotelsInCityByProximity("petah tikva",50,85);
        assertArrayEquals(emptyarray, proxCityArray3);
        //empry text
        Hotel[] proxCityArray5 = emptyBoopingSite.getHotelsInCityByProximity("delhi",50,85);
        assertArrayEquals(emptyarray, proxCityArray3);

    }
}
