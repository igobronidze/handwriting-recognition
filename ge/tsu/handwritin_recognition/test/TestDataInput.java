package ge.tsu.handwritin_recognition.test;

import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestDataInput {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
//        TestData data = DataCreatorConsole.createByScanner(false);
//        System.out.println(data);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SystemParameter.testDataPath + "\\test stage 1\\4.txt"));
        System.out.println(in.readObject().toString());
    }
}
