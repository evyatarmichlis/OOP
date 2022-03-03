import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * Comparator class use in the first method use ro compere by rate and name
 */

public class HotelsCompereByRate implements Comparator<Hotel> {


    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating()< hotel2.getStarRating()) return 1;
        if (hotel1.getStarRating() > hotel2.getStarRating()) return -1;
        else return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
    }
}
