import java.io.*;
import java.util.*;

public class Producer extends Thread {
    private final BoundedBuffer buffer;
    String[] fileNames;
    int batchSize = 100;

    /**
     * Create a Producer
     * 
     * @param buff The buffer into which the producer queues
     */
    public Producer(BoundedBuffer buff, String[] fileNames) {
        this.buffer = buff;
        this.fileNames = fileNames;
    }

    /**
     * Open up files given the file names from an array of Strings. Every word from
     * files gets inserter into BoundedBuffer
     */
    public void run() {
        try {
            Scanner sc = null;
            ArrayList<String> words = new ArrayList<String>(); 
            for (String s : fileNames) {
                File f = new File(s);
                try {
                    sc = new Scanner(f);
                    while (sc.hasNext()) {
                    String word = sc.next();
                    //produce a batch of words (array or list)
                    words.add(word);
                    if(words.size() == batchSize){
                        buffer.produce(words);
                        words = new ArrayList<String>();
                    }
                }
                    if (words.size() < batchSize){
                        buffer.produce(words);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Cannot open scanner");
                    System.exit(1);
                } 
             }
             buffer.produce(null);
            
        } catch (InterruptedException e) {
        }
    }
}