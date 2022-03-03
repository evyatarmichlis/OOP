/**the class represent a book from a library
 * the book can be taken by patron from the library
 * @author Evyatar michlis
 */

public class Book {
    /**
     * the book have a Title,author, year of publish
     *  and a value(comic , drama, education)
     */


    String bookTitle;
    String bookAuthor;
    int bookYearOfPublication;
    int bookComicValue;
    int bookDramaticValue;
    int bookEducationalValue;
    int borrowerid = -1;
    boolean returnBook = false;
    boolean bookAvailable = true;

    /**
     * the Constructor of the class
     * @param bookTitle = the book title
     * @param bookAuthor = the book Author
     * @param bookYearOfPublication = the year of publication of the book
     * @param bookComicValue = the book comic value
     * @param bookDramaticValue = the book dramatic value
     * @param bookEducationalValue =   the book Educational Value
     */
    Book(String bookTitle, String bookAuthor,
         int bookYearOfPublication, int bookComicValue, int bookDramaticValue,int bookEducationalValue){

        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYearOfPublication = bookYearOfPublication;
        this.bookComicValue = bookComicValue;
        this.bookDramaticValue = bookDramaticValue;
        this.bookEducationalValue = bookEducationalValue;


    }
    /**
     * @return a string Representation of the book
     */
    String stringRepresentation(){

        int sum =  this.bookComicValue + this.bookDramaticValue +  this.bookEducationalValue;
        String string_sum = String.valueOf(sum);
        return("["+this.bookTitle +","+ this.bookAuthor+"," + this.bookYearOfPublication +","+ string_sum+"]");

    }
    /**
     * @return the sum if the values
     */
    int getLiteraryValue(){

        return  this.bookComicValue + this.bookDramaticValue +  this.bookEducationalValue;
    }
    /**
     * set the borrower id to this book
     */
    void setBorrowerId(int borrowerId){

        this.borrowerid = borrowerId;


    }
    /**
     * @return the current id of the borrower
     */
    int getCurrentBorrowerId(){

        return this.borrowerid;

    }
    /**
     * Marks this book as returned.
     */
    void returnBook(){

        if(!this.returnBook){
            setBorrowerId(-1);
            this.returnBook = true;
        }


    }




}
