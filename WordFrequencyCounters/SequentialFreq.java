import java.io.*;
import java.util.*;


public class SequentialFreq {

    Hashtable<String,Integer> map = new Hashtable<String,Integer>();
    
    //open the file and populate the map with words and their counts
    public void count(String fileName) {
        
        File f = new File(fileName); 
        Scanner sc = null;
        try {
          sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open scanner");
            System.exit(1);
        };
        
        while (sc.hasNext()) {
            //System.out.println("next word is: " + sc.next());
            
            //the current word
            String word = sc.next(); 
            
            if (map.containsKey(word)) {
                //increment frequency
                int freq = map.get(word);
                map.put(word, (freq+1));
            } else {
                //insert this new word with frequency=1
                map.put(word, 1);
            }
        }
         
        //System.out.println("done counting");
        
    }
    
    //prints the words in map and their frequencies
    public void print() {
        
        Set<String> set = map.keySet();
        Object[] words = set.toArray();
        Arrays.sort(words);
    
        for (Object s:words) {
            //get the frequency from the map 
            int freq = (int)map.get(s);
            System.out.println(s + ": " + freq);
        }
    }

    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("usage: WordFreq <filename>");
            System.exit(1);
        }
        SequentialFreq obj = new SequentialFreq();
        for(String f: args){
            obj.count(f);
        }
        obj.print();
    }

}
