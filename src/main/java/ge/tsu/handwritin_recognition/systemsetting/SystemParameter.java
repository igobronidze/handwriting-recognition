package ge.tsu.handwritin_recognition.systemsetting;

import ge.tsu.handwritin_recognition.model.CharsSet;

public class SystemParameter {

    public static final CharsSet charsSet = new CharsSet('Z' - 'A' + 1, (int)'A');

    public static final String testDataPath = "C:\\dev\\handwriting-recognition\\testdata";

    public static final String neuralNetworkPath = "C:\\dev\\handwriting-recognition\\network\\network.nnet";

    public static final int[] neuralInHiddenLayers = new int[]{};

    public static final int numberOfDataSetRowInOneTraining = 10;

    public static final int testDataCreatorGridDefaultWidth = 15;

    public static final int testDataCreatorGridDefaultHeight = 15;

    public static final int testDataCreatorGridMaxWidth = 100;

    public static final int testDataCreatorGridMaxHeight = 100;

    public static final int testDataCreatorWindowWidth = 900;

    public static final int testDataCreatorWindowHeight = 650;

    public static final String testDataCreatorDarkColor = "#001a1a";

    public static final String testDataCreatorBrightColor = "#e6ffff";
}
