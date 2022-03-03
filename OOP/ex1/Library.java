/**
 *  the class represent a library , we can add books to the library ,
 *  we can register Patron to the library .
 *  also we can borrow and return books .
 * @author  Evyatar michlis
 */

public class Library {
    int maxBookCapacity;
    int maxBorrowedBooks;
    int maxPatronCapacity;
    Book[] booksArry;
    Patron[] patronArry;
    int[] borrowRecordOfPatron ;
    int[] bestBook;
    int negativeNumber = -1;

    /**
     *  the Constructor of the class:
     * @param maxBookCapacity =  how many books the library can store
     * @param maxBorrowedBooks = how many the partons can borrow from the library
     * @param maxPatronCapacity = how many patrons can be register
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        this.booksArry = new Book[maxBookCapacity];
        this.patronArry = new Patron[maxPatronCapacity];
        this.borrowRecordOfPatron = new int[maxPatronCapacity];
        this.bestBook = new int[maxBookCapacity];

    }

    /**
     *
     * @param book type
     *       check if we can add book to the library ( if we have a empty slot)
     * @return if we add a book or if the book exists return is location
     * else return -1
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i <  maxBookCapacity; i++) {
            if (booksArry[i] == null) {
                booksArry[i] = book;
                return (i);
            }
            else if  (booksArry[i] == book) {

                return i;
            }

         }
        return negativeNumber;
    }

    /**
     *
     * @param bookId = the index of the wanted book
     * @return if the id is ok return true
     * else return false
     */

    boolean isBookIdValid(int bookId) {
        if(bookId > maxBookCapacity){
            return false;
        }
        for (int i = 0; i < maxBookCapacity; i += 1) {
            if (booksArry[i] != null && i == bookId) {
                return true;
            }
        }
        return false;
        }

    /**
     *
     * @param book = the book we want to check
     * @return the book location
     */

    int getBookId(Book book){
        for (int i = 0; i <= maxBookCapacity; i++) {
            if (book == booksArry[i]) {
                return i;
            }
        }

         return negativeNumber;


    }

    /**
     *
     * @param bookId the book location
     * @return if the book is available to the patron
     */
    boolean isBookAvailable(int bookId){
        for (int i = 0; i < maxBookCapacity; i++) {
            if (booksArry[i] != null && i == bookId &&
                     booksArry[i].borrowerid ==negativeNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param patron = check if he can be register to the library
     * @return if the patron can be register return his id in the library
     * else return negative Number
     */
    int registerPatronToLibrary(Patron patron){
        for (int i = 0; i < maxPatronCapacity; i++){
            if (patronArry[i]==null ){
                patronArry[i]=patron;
                return  i ;

            }
            else if(patron == patronArry[i]){
                return i;

            }
        }
        return -1;
    }

    /**
     *
     * @param patronId = check if this patron is valid patron
     * @return true if he is a valid patron to the library
     * false if he is not valid
     */
    boolean isPatronIdValid(int patronId) {
        if(patronId>maxPatronCapacity){
            return false;
        }
        for (int i = 0; i < maxPatronCapacity; i++) {
            if (patronArry[i] != null && i == patronId) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param patron =  the current patron
     * @return the id of the patron
     */
    int getPatronId(Patron patron){
        for (int i = 0; i < maxPatronCapacity; i++){
            if (patron == patronArry[i]){
                return i;
            }

        }
        return negativeNumber;

    }

    /**
     *
     * @param bookId = the book the patron want to take
     * @param patronId = the patron
     * @return return true if this patron can take this book
     * return false if he can't
     * (for example if the book is not available or the id isn't valid)
     */
    boolean borrowBook(int bookId,int patronId){


             if(!isBookIdValid(bookId)||!isPatronIdValid(patronId)
                    || borrowRecordOfPatron[patronId] > maxBorrowedBooks){
                return false;
                }
            else if(patronArry[patronId].willEnjoyBook(booksArry[bookId]) &&
                    this.isBookAvailable(bookId)
                     && maxBorrowedBooks > borrowRecordOfPatron[patronId] ) {
                booksArry[bookId].borrowerid = patronId;

                patronArry[patronId].borowdBooks +=1 ;
                borrowRecordOfPatron[patronId] += 1;

                return true;
             }
            else {
                return false;
                }
        }

    /**
     * change the parameters to mark the book is return
     * @param bookId = the book we want to return
     *
     */
    void returnBook(int bookId){
        if(isBookIdValid(bookId)) {
            borrowRecordOfPatron[booksArry[bookId].borrowerid] -= 1;
            booksArry[bookId].borrowerid = negativeNumber;
        }

    }

    /**
     * this function with loop over all of the books and
     * return the best book for our patron , base on his tendency
     * @param patronId = the patron we need to suggest the best book for him
     * @return the best book for the patron or null if
     * the right book don't exist
     */


    Book suggestBookToPatron(int patronId) {

        int maxSofar = 0;
        Book suggestbook = null;
        for (int i = 0; i < maxBookCapacity; i += 1) {
            if (isPatronIdValid(patronId) && patronArry[patronId].willEnjoyBook(booksArry[i]) && isBookAvailable(i) &&
                    patronArry[patronId].getBookScore(booksArry[i]) > maxSofar) {
                maxSofar = patronArry[patronId].getBookScore(booksArry[i]);
                suggestbook = booksArry[i];
            }

        }
        return suggestbook;
    }


}
