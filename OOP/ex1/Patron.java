/**the class represent a patron to the library
 * the patron can take books from the library if he will enjoy from them
 * @author Evyatar michlis
 */

public class Patron {

    String patronFirstName;
    String patronLastName ;
    int comicTendency ;
    int dramaticTendency;
    int educationalTendency;
    int patronEnjoymentThreshold;
    int borowdBooks;


    /**
     * the Constructor of the class:
     * @param patronFirstName = the first name of the patron
     * @param patronLastName = the last name of the patron
     * @param comicTendency = the tendency of comic
     * @param dramaticTendency = the tendency of drama
     * @param educationalTendency = the tendency of educational value
     * @param  patronEnjoymentThreshold = the enjoyment threshold of the patron
     */
    Patron(String patronFirstName,
           String patronLastName,
           int comicTendency,
           int dramaticTendency,
           int educationalTendency,
           int patronEnjoymentThreshold){

        this.patronFirstName = patronFirstName;
        this.patronLastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.patronEnjoymentThreshold = patronEnjoymentThreshold;
    }
    /**
     * @return a string representation of the patron
     */
    String stringRepresentation(){

        return this.patronFirstName + " " + this.patronLastName;

    }
    /**
     * @param book = the score of a book from book class
     * @return the score of the book base on the patron tendency for each value
     */
    int getBookScore(Book book){
        return  (this.comicTendency * book.bookComicValue)
                + (this.dramaticTendency * book.bookDramaticValue)
                +  (this.educationalTendency * book.bookEducationalValue);
    }

    /**
     *
     * @param book = the book the patron want to take
     * @return true if he will enjoy the book . false if not
     */
    boolean willEnjoyBook(Book book){
        return this.getBookScore(book) >= patronEnjoymentThreshold;
    }


}
