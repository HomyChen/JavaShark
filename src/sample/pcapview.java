package sample;

/**
 * Created by Ariest on 2017-03-27.
 */
public class pcapview {
    public pcap pFile;
    public pcapview(pcap p){
        this.pFile = p;
    }
    public void fileNameView(){
        System.out.println("File name is : "+ pFile.FileAddress);
    }
}
