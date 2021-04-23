import java.util.*;

public class BoundedBuffer {
    protected int numSlots; 
    private ArrayList<ArrayList<String>> buffer;
    Boolean readingFinished = false;
 
    public BoundedBuffer(int numSlots) { 
       if(numSlots <= 0) {
          throw new IllegalArgumentException("numSlots <= 0");
       } 
       this.numSlots = numSlots; 




       buffer = new ArrayList<ArrayList<String>>(numSlots);
    }
  
      /**
       * produce an item in the bounded buffer.  Block if full.
       * @param word the thing to add to the rear of the buffer
       * @throws InterruptedException
       */
    public synchronized void produce(ArrayList<String> words) throws InterruptedException {
      //synchronized(buffer){
         //System.out.println("P: Trying to produce " + word);
         while (buffer.size() == numSlots) {
            //System.out.println("P: Buffer full");
            wait();
            //System.out.println("P: Producer got notification from consumer");
         }
         buffer.add(words);
         //System.out.println("P: Added " + word + " to buffer");
         notifyAll();
         //System.out.println("P: Producer is notifying");
      //} 
    }
 
      /** 
       * Remove an item from a bounded buffer.  Block if empty
       * @return the item removed
       * @throws InterruptedException
       */
    public synchronized ArrayList<String> consume() throws InterruptedException {
      //System.out.println("C: About to consume");
      //notifyAll();
      while (buffer.size() == 0) {
         //System.out.println("C: Buffer empty");
         wait();
         //System.out.println("C: Done waiting");
      }
       
      ArrayList<String> words = buffer.get(0);
      buffer.remove(words);
      notifyAll();
      //System.out.println("C: About to return " + word);
      return words;
    }
 }