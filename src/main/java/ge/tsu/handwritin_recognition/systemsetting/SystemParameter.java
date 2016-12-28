package ge.tsu.handwritin_recognition.systemsetting;

import ge.tsu.handwritin_recognition.model.CharsSet;

public class SystemParameter {

    public static final CharsSet charsSet = new CharsSet(33, 3200);

    public static final String testDataPath = "C:\\dev\\handwriting-recognition\\testdata";

    public static final String neuralNetworkPath = "C:\\dev\\handwriting-recognition\\neural_network";

    public static final int[] neuralInHiddenLayers = new int[]{20};

    public static final int testDataCreatorGridDefaultWidth = 15;

    public static final int testDataCreatorGridDefaultHeight = 15;

    public static final int testDataCreatorGridMaxWidth = 100;

    public static final int testDataCreatorGridMaxHeight = 100;

    public static final int testDataCreatorWindowWidth = 900;

    public static final int testDataCreatorWindowHeight = 650;

    public static final String testDataCreatorDarkColor = "#001a1a";

    public static final String testDataCreatorBrightColor = "#e6ffff";
}
