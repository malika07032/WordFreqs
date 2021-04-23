import java.io.*;
import java.util.*;

public class ByFirstLetterFreq {
	// ArrayList of Hashtables
	List<Hashtable<String, Integer>> mainTable = new ArrayList<Hashtable<String, Integer>>();

	ByFirstLetterFreq() {
		// create a Hashtable for every letter in the alphabet and store them
		// in the slots of ArrayList accordingly
		// For example: Hashtable for words starting with "A" will have index of 0 in
		// the ArrayList
		for (int i = 0; i < 27; i++) {
			Hashtable<String, Integer> wordmap = new Hashtable<String, Integer>();
			mainTable.add(i, wordmap);
		}
	}

	public void count(String filename) {

		File f = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("cannot open scanner");
			System.exit(1);
		}

		while (sc.hasNext()) {
			// the current word
			String word = sc.next();
			char firstL = word.charAt(0);
			// convert first character to uppercase and substract 65 from its ASCII
			// representation to mainTable it to the index of the array
			// words starting with "A" will be stored at mainTable[0]
			int index = (int) Character.toUpperCase(firstL) - 65;
			// Allow only indices from 0 to 26(from A-Z)
			Hashtable<String, Integer> letterMap = (index >= 0 && index < 26) ? mainTable.get(index + 1) : mainTable.get(0);
			synchronized (letterMap) {
					if (letterMap.containsKey(word)) {
						// increment frequency
						int freq = (int) letterMap.get(word);
						letterMap.put(word, (Integer) (freq + 1));
					} else {
						letterMap.put(word, (Integer) 1);
					}
				}
			
		}
	}

	public void print() {
		System.out.println("Word Table call");
		for (Hashtable<String, Integer> map : mainTable) {
			Set<String> set = map.keySet();
			Object[] words = set.toArray();
			Arrays.sort(words);

			for (Object s : words) {
				int freq = (int) map.get(s);
				System.out.println(s + ": " + freq);
			}
		}
	}

	public static void main(String[] args) {
		// checking if args has at least one file
		if (args.length < 2) {
			System.out.println("usage: WordFreq <filename>");
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
		// creating an instance of the ByFirstLetterFreq class
		ByFirstLetterFreq arrOfHash = new ByFirstLetterFreq();
		// creating an array of threads
		ByFirstLetterThread[] firstTable = new ByFirstLetterThread[numThreads];
		// each thread corresponds to a file in args
		for (int i = 0; i < numThreads; i++) {
			// for each index in args creating an instance of CountingThread3
			ByFirstLetterThread first = new ByFirstLetterThread(i, numThreads, files, arrOfHash);
			// inserting a thread in the array of threads
			firstTable[i] = first;
			// running a thread
			first.start();
		}

		for (ByFirstLetterThread t : firstTable) {
			try {
				// holds the execution of the thread currently running until it's finished
				// next thread won't be executed, unless the first one is finished
				t.join();
			} catch (InterruptedException exception) {
			}
		}
		arrOfHash.print();
	}
}