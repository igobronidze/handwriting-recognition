package ge.edu.tsu.handwriting_recognition.control_panel.console.utils;

import ge.edu.tsu.handwriting_recognition.control_panel.console.resources.Messages;

public class DateUtils {

    public static String getFullDateFromSecond(long second) {
        int sec = (int)(second % 60);
        second /= 60;
        int min = (int)(second % 60);
        second /= 60;
        int hour = (int)(second % 24);
        int day = (int)(second / 24);
        String result = "";
        result += day + Messages.get("dayShort") + " ";
        result += hour + Messages.get("hourShort") + " ";
        result += min + Messages.get("minuteShort") + " ";
        result += sec + Messages.get("secondShort");
        return result;
    }
}
