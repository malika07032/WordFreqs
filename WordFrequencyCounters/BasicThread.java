
public class BasicThread extends java.lang.Thread{
    String[] filenames;
    int threadCount;
    int threadID;
    BasicThreadedFreq table;

    BasicThread(int threadID, int threadCount, String[] filenames, BasicThreadedFreq table) { 
        this.threadID = threadID;
        this.threadCount = threadCount;
        this.filenames = filenames; 
        this.table = table;
    }

    public void run(){
        //System.out.println(threadID);
        for (int i = 0 ; i < filenames.length - 1; i++){
            int fileID = threadID + (threadCount*i);
            System.out.println(fileID);
            if (fileID < filenames.length){
                table.count(filenames[fileID]);
            }
        }
    }
}
