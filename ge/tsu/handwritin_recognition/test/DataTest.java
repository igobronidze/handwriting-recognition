package ge.tsu.handwritin_recognition.test;

import ge.tsu.handwritin_recognition.datacreator.DataCreatorConsole;
import ge.tsu.handwritin_recognition.data.InputData;

public class DataTest {
    
    public static void main(String[] args) {
        
        InputData data = DataCreatorConsole.createByScanner(false);
        System.out.println(data);
        
    }
    
}
