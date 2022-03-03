import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * this class check the time of different actions on different data stractors
 */
public class  SimpleSetPerformanceAnalyzer {
    /*
    build the data structure
     */
    private static LinkedList<String> linkedList = new LinkedList<String>();
    private static TreeSet<String> treeSet = new TreeSet<String>();
    private static HashSet<String> hashSet = new HashSet<String>();
    private static OpenHashSet openHashSet;
    private static ClosedHashSet closedHashSet;

    private static CollectionFacadeSet facadeLinkList;
    private static CollectionFacadeSet facadeTree;
    private static CollectionFacadeSet facadeSet;
    private static int toMilliSec = 1000000;
    private static int warmUp = 70000;
    private static String[] wordsData1;
    private static String[] wordsData2;


    /**
     * Contractor
     */
    SimpleSetPerformanceAnalyzer() {
        facadeLinkList = new CollectionFacadeSet(linkedList);
        facadeTree = new CollectionFacadeSet(treeSet);
        facadeSet = new CollectionFacadeSet(hashSet);
        openHashSet = new OpenHashSet();
        closedHashSet = new ClosedHashSet();
        wordsData1 = Ex4Utils.file2array("src/data1.txt");
        wordsData2 = Ex4Utils.file2array("src/data2.txt");
//        1: long timeBefore = System.nanoTime();
//        2: /* ... some operations we wish to time */
//        3: long difference = System.nanoTime() - timeBefore;

    }

    /**
     * the main method . activate the other methods to chrck time
     * @param args
     */
    public static void main(String[] args) {
        /*
        test the load of the first data file
         */
        SimpleSetPerformanceAnalyzer performanceAnalysis = new  SimpleSetPerformanceAnalyzer();
        /*
        test of data1.txt
         */
        System.out.println("*****DATA1 TEST*****");
        System.out.println("link list time :");
        testData(facadeLinkList, wordsData1);
        System.out.println("tree set time :");
        testData(facadeTree, wordsData1);
        System.out.println("hash set time :");
        testData(facadeSet, wordsData1);
        System.out.println("open hash set time :");
        testOpenHash(wordsData1);
        System.out.println("close hash time time :");
        testCloseHash(wordsData1);
                /*
        test of data2.txt
         */

        System.out.println("*****DATA2 TEST*****");
        System.out.println("link list time :");
        testData(facadeLinkList, wordsData2);
        System.out.println("tree set time :");
        testData(facadeTree, wordsData2);
        System.out.println("hash set time :");
        testData(facadeSet, wordsData2);
        System.out.println("open hash set time :");
        testOpenHash(wordsData2);
        System.out.println("close hash time time :");
        testCloseHash(wordsData2);

        /*
        warm up before the contain test
         */
        restartDataStructure(wordsData1);

        System.out.println("*****CONTAIN HI IN DATA 1*****");

        System.out.println("link list time :");
        containCheck(facadeLinkList,"hi",warmUp/10);
        System.out.println("tree set time :");
        containCheck(facadeTree,"hi",warmUp);
        System.out.println("hash set time :");
        containCheck(facadeSet,"hi",warmUp);
        System.out.println("open hash set time :");
        containCheck(openHashSet,"hi",warmUp);
        System.out.println("close hash time time :");
        containCheck(closedHashSet,"hi",warmUp);


        System.out.println("*****CONTAIN -1317089015 IN DATA 1*****");

        System.out.println("link list time :");
        containCheck(facadeLinkList,"-13170890158",warmUp/10);
        System.out.println("tree set time :");
        containCheck(facadeTree,"-13170890158",warmUp);
        System.out.println("hash set time :");
        containCheck(facadeSet,"-13170890158",warmUp);
        System.out.println("open hash set time :");
        containCheck(openHashSet,"-13170890158",warmUp);
        System.out.println("close hash time time :");
        containCheck(closedHashSet,"-1317089015",warmUp);

        restartDataStructure(wordsData2);

        System.out.println("*****CONTAIN 23 IN DATA 2*****");

        System.out.println("link list time :");
        containCheck(facadeLinkList,"23",warmUp/10);
        System.out.println("tree set time :");
        containCheck(facadeTree,"23",warmUp);
        System.out.println("hash set time :");
        containCheck(facadeSet,"23",warmUp);
        System.out.println("open hash set time :");
        containCheck(openHashSet,"23",warmUp);
        System.out.println("close hash time time :");
        containCheck(closedHashSet,"23",warmUp);


        System.out.println("*****CONTAIN HI IN DATA 2*****");

        System.out.println("link list time :");
        containCheck(facadeLinkList,"hi",warmUp/10);
        System.out.println("tree set time :");
        containCheck(facadeTree,"hi",warmUp);
        System.out.println("hash set time :");
        containCheck(facadeSet,"hi",warmUp);
        System.out.println("open hash set time :");
        containCheck(openHashSet,"hi",warmUp);
        System.out.println("close hash time time :");
        containCheck(closedHashSet,"hi",warmUp);


    }

    /**
     *enter the words to the collection
     */

    public static void enterIntoCollection(SimpleSet simpleSet, String[] words) {
        for (String word : words) {
            simpleSet.add(word);
        }
    }

    /**
     * test adding data to the collection
     */

    public static void testData(SimpleSet obj, String[] words) {
        long timeBefore = System.nanoTime();
        enterIntoCollection(obj, words);
        long difference = System.nanoTime() - timeBefore;
        System.out.println(difference / toMilliSec);


    }

    /**
     * test adding data to the open hash
     */

    public static void testOpenHash(String[] words) {
        long timeBefore = System.nanoTime();
        OpenHashSet openHashSet = new OpenHashSet(words);
        long difference = System.nanoTime() - timeBefore;
        System.out.println(difference / toMilliSec);

    }
    /**
     * test adding data to the close hash
     */


    public static void testCloseHash(String[] words) {
        long timeBefore = System.nanoTime();
        ClosedHashSet closedHashSet = new ClosedHashSet(words);
        long difference = System.nanoTime() - timeBefore;
        System.out.println(difference / toMilliSec);
    }

    /**
     * warmUp before the contain test
     */
    public static void warmUp(SimpleSet obj,String word,int n) {
        for (int i = 0; i < n; i++) {
            obj.contains(word);
        }
    }

    /**
     * restart the Data Structures so we can test other things on them
     * add the words into the collection
     */
    public static void  restartDataStructure(String[] words){
        facadeLinkList = new CollectionFacadeSet(linkedList);
        facadeTree = new CollectionFacadeSet(treeSet);
        facadeSet = new CollectionFacadeSet(hashSet);
        openHashSet = new OpenHashSet(words);
        closedHashSet = new ClosedHashSet(words);
        enterIntoCollection(facadeLinkList,words);
        enterIntoCollection(facadeSet,words);
        enterIntoCollection(facadeTree,words);


    }

    /**
     * check the different test to the contain methods
     */
    public static void containCheck(SimpleSet simpleSet , String word ,int n){
        long total = 0;
        warmUp(simpleSet,word,n);
        for (int i = 0; i < n; i++){
            long timeBefore = System.nanoTime();
            simpleSet.contains(word);
            total += System.nanoTime() - timeBefore;

        }
        System.out.println(  total/n);
    }
}






