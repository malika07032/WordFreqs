
public class ByFirstLetterThread extends java.lang.Thread{
    String[] filenames;
    int threadCount;
    int threadID;
    ByFirstLetterFreq table;

    public ByFirstLetterThread(int threadID, int threadCount, String[] filenames, ByFirstLetterFreq table) { 
        this.threadID = threadID;
        this.threadCount = threadCount;
        this.filenames = filenames; 
        this.table = table;
    }

    public void run(){
        //table.count(filename);
        for (int i = 0 ; i < filenames.length - 1; i++){
            int fileID = threadID + (threadCount*i);
            System.out.println(fileID);
            if (fileID < filenames.length){
                table.count(filenames[fileID]);
            }
        }
    }
}