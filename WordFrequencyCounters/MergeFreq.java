import java.io.*;
import java.util.*;
/** 
 *  MergeFreq, multithreaded implementation of word frequency counter
 * 	Each worker thread builds a private word frequency table.
 *  After each worker thread is done, the main thread merges that worker 
 *  thread’s private table into the main table.
*/


public class MergeFreq {

	Hashtable<String,Integer> map = new Hashtable<String,Integer>();
	
	//open the file and populate the map with words and their counts
	//preventing multiple threads from executing the same code at one time
	public void count(String fileName) {
		
		File f = new File(fileName); 
		Scanner sc = null;
		try {
		  sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("cannot open scanner");
			System.exit(1);
		};
		
		while (sc.hasNext()) {
			//the current word
			String word = sc.next(); 
			
			//synchronized (this) {
			if (map.containsKey(word)) {
				//increment frequency
				int freq = (int)map.get(word);
				map.put(word, (Integer)(freq+1));
			} else {
				map.put(word, (Integer)1);
			}
			//}
		}
	}
	
	//Helper function for merging 2 Hashtables
	static void merge(MergeFreq mainT,MergeFreq threadT){
		//loop through the entries in the thread Hashtable
		for(Map.Entry<String,Integer> entry : threadT.map.entrySet()){
			//if main table already has the key from thread
			//add its frequency to the main table's key value
			if(mainT.map.containsKey(entry.getKey())){
				int freq = (int)mainT.map.get(entry.getKey());
				mainT.map.put(entry.getKey(), (Integer)(freq+entry.getValue()));
			//if there is no such key in main table, add it with its frequency
			}else{
				mainT.map.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void print() {
		System.out.println("Word Table call");
		Set<String> set = map.keySet();
		Object[] words = set.toArray();
		Arrays.sort(words);
	
		for (Object s:words) {
			int freq = (int)map.get(s);
			System.out.println(s + ": " + freq);
		}
	}

	public static void main(String[] args) {
		//checking if args has at least one file
		if (args.length < 2) {
			System.out.println("usage: WordFreq <fileName>");
			System.exit(1);
		}
		int numThreads = Integer.valueOf(args[0]);
        if (numThreads > args.length-1){
            numThreads = args.length-1;
        }
        String[] files = new String[args.length-1];
        for(int i=0; i< args.length-1; i++){
            files[i] = args[i+1];
        }
		//creating an instance of the NewWordFreq class
		MergeFreq mainTable = new MergeFreq(); 
		//creating an array of threads
		MergeThread[] firstTable = new MergeThread[numThreads];
		//each thread corresponds to a file in args
		for(int i = 0 ; i < numThreads ; i++){
			//for each index in args creating an instance of CountingThread2
			MergeThread first = new MergeThread(i, numThreads, files);
			//inserting a thread in the array of threads
			firstTable[i] = first;
			//running a thread
			first.start();
			
		}
		
		for(MergeThread t : firstTable){
			try{
				//holds the execution of the thread currently running until it's finished
				//next thread won't be executed, unless the first one is finished
				t.join();
				//merge a table created by a thread with the main table
				merge(mainTable, t.table);
			}
			catch(InterruptedException exception){
			}
		}
		mainTable.print();
	}
}


