import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**c
 * compere hotels by distance and if not by point of inters
 */
public class HotelDistanceCompere implements Comparator<Hotel> {
    double latitude;
    double longitude;

    HotelDistanceCompere(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }


    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        double distance1 = Math.sqrt((Math.pow(hotel1.getLatitude() - this.latitude, 2)) + Math.pow((hotel1.getLongitude() - this.longitude), 2));
        double distance2 = Math.sqrt((Math.pow(hotel2.getLatitude() - this.latitude, 2)) + Math.pow((hotel2.getLongitude() - this.longitude), 2));
        if (distance1 > distance2) return 1;
        if (distance2 > distance1) return -1;
        else {
            return hotel2.getNumPOI() - hotel1.getNumPOI();
        }
    }
}
