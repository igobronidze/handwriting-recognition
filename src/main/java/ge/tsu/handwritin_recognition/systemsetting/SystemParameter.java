package ge.tsu.handwritin_recognition.systemsetting;

import ge.tsu.handwritin_recognition.model.CharactersSet;

public class SystemParameter {

    // ############################ Neuroph parameters
    private static final char firstSymbol = 'A';

    private static final char lastSymbol = 'Z';

    public static final CharactersSet CHARACTERS_SET = new CharactersSet(firstSymbol, lastSymbol);

    public static final String testDataPath = "D:\\sg\\handwriting_recognition\\testdata";

    public static final String neuralNetworkPath = "D:\\sg\\handwriting_recognition\\network\\network.nnet";

    public static final int[] neuralInHiddenLayers = new int[]{};

    public static final int numberOfDataSetRowInEachTraining = Integer.MAX_VALUE;

    // ############################ DataCreatorWithGrid parameters
    public static final int testDataCreatorGridDefaultWidth = 5;

    public static final int testDataCreatorGridDefaultHeight = 5;

    public static final int testDataCreatorGridMaxWidth = 100;

    public static final int testDataCreatorGridMaxHeight = 100;

    public static final int testDataCreatorWindowWidth = 900;

    public static final int testDataCreatorWindowHeight = 650;

    public static final String testDataCreatorDarkColor = "#001a1a";

    public static final String testDataCreatorBrightColor = "#e6ffff";
}
