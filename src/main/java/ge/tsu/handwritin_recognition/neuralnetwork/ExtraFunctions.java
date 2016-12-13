package ge.tsu.handwritin_recognition.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

public class ExtraFunctions {

    public static double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-1 * z));
    }

    public static double[] sigmoid(double z[]) {
        double ans[] = new double[z.length];
        for (int i = 0; i < z.length; i++) {
            ans[i] = sigmoid(z[i]);
        }
        return ans;
    }

    public static List<Double> sigmoid(List<Double> z) {
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < z.size(); i++) {
            ans.add(sigmoid(z.get(i)));
        }
        return ans;
    }
}
