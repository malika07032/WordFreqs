import java.util.*;
public class BoundedBufferTable {
    Hashtable<String,Integer> table = new Hashtable<String,Integer>();

    public void count(ArrayList<String> words) {
        for (String word : words){
            synchronized (this){
                if (table.containsKey(word)) {
                    //increment frequency
                    int freq = (int)table.get(word);
                    table.put(word, (Integer)(freq + 1));
                } else {
                    table.put(word, (Integer)1);
                }
            }
            
        }
        
    }

    public void print() {
		System.out.println("Word Table call");
		Set<String> set = table.keySet();
		Object[] words = set.toArray();
		Arrays.sort(words);
        System.out.println("Word Table call here:" + words.length);
		for (Object s:words) {
			int freq = (int)table.get(s);
			System.out.println(s + ": " + freq);
		}
	}

    public static void main(String[] args){
        System.out.println("program starting");
        BoundedBuffer buffer = new BoundedBuffer(5);
        if (args.length < 1) {
            System.out.println("usage: WordFreq <filename>");
            System.exit(1);
        }
        //Producer first;
        BoundedBufferTable table = new BoundedBufferTable();
		
        Producer prod = new Producer(buffer, args);
        Consumer cons = new Consumer(buffer, table);
        prod.start();
        cons.start();
        try {
            prod.join();
            cons.join();
        } catch (InterruptedException e) {}
        
        table.print();
    }
}