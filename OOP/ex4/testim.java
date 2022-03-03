public class testim {
    public static void main(String[] args) {
        String[] strings = new String[]{"a","b"};
        ClosedHashSet closedHashSet = new ClosedHashSet(0.5f,0.2f);
        closedHashSet.add("hi");
        closedHashSet.delete("hi");
        System.out.println(closedHashSet.contains("hi"));
        closedHashSet.add("hi");
        System.out.println(closedHashSet.contains("hi"));
        closedHashSet.add("b");
        closedHashSet.add("c");
        closedHashSet.add("d");
//        closedHashSet.add("d");
//        closedHashSet.add("e");
//        closedHashSet.add("a");
//        closedHashSet.add("s");
//        closedHashSet.add("c");
//        closedHashSet.add("x");
//        closedHashSet.add("f");
//        closedHashSet.add("sad");
//        closedHashSet.add("aaaaaaa");
        for (int i = 0; i < 40; i++) {
            String str1 = Integer.toString(i);
            closedHashSet.add(str1);
        }
    }
}



//       OpenHashSet openHashSet = new OpenHashSet();
//       openHashSet.add("hi");
//       openHashSet.add("hi");
//       openHashSet.add("ih");
//       openHashSet.delete("hi");
//       System.out.println(openHashSet.contains("hi"));
//       openHashSet.add("pollinating sandboxes");
//       openHashSet.add("amusement & hemophilias");
//       System.out.println(openHashSet.contains("amusement & hemophilias"));
//       openHashSet.add("asa");
//       openHashSet.add("sss");
//       openHashSet.delete("sss");
//       openHashSet.delete("asa");
//       openHashSet.add("ddegf");
//       openHashSet.add("asddffc");






