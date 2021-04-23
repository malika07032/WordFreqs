
public class MergeThread extends java.lang.Thread{
    String[] filenames;
    int threadCount;
    int threadID;
    MergeFreq table;

    public MergeThread(int threadID, int threadCount, String[] filenames) { 
        this.threadID = threadID;
        this.threadCount = threadCount;
        this.filenames = filenames; 
        table = new MergeFreq();
    }

    public void run(){
        //table.count(filename);
        for (int i = 0 ; i < filenames.length - 1; i++){
            int fileID = threadID + (threadCount*i);
            //System.out.println(fileID);
            if (fileID < filenames.length){
                table.count(filenames[fileID]);
            }
        }
    }
}
