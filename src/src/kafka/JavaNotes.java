package src.kafka;

import java.util.TreeMap;

public class JavaNotes {

}
class TreeMapUsage{

    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "val");
        treeMap.put(4, "val");
        treeMap.put(8, "val");
        treeMap.put(12, "val");
        treeMap.put(10, "val");


//        System.out.println(treeMap.firstKey());//1
//        System.out.println(treeMap.lastKey());//12
//        System.out.println(treeMap.keySet());//[1, 4, 8, 10, 12]

        System.out.println(treeMap.tailMap(8)); //TailMap inclusive
        System.out.println(treeMap.headMap(8)); //head map exclusive
        System.out.println(treeMap.headMap(9));


        System.out.println(treeMap.floorKey(5));
        System.out.println(treeMap.floorKey(4));//inclusive

        System.out.println(treeMap.ceilingKey(5));
        System.out.println(treeMap.ceilingKey(4));//inclusive

        System.out.println(treeMap.get(5));
        System.out.println(treeMap.get(4));
    }
}
