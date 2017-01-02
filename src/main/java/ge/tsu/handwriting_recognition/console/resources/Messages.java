package ge.tsu.handwriting_recognition.console.resources;

import ge.tsu.handwriting_recognition.service.SystemParameterService;
import ge.tsu.handwriting_recognition.service.SystemParameterServiceImpl;
import ge.tsu.handwriting_recognition.systemsetting.SystemParameter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Messages {

    private static SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private static Map<String, Properties> messages = new HashMap<>();

    public static String get(String key, String langCode) {
        if (key != null) {
            Properties properties = messages.get(langCode);
            if (properties == null) {
                properties = new Properties();
                try {
                    InputStream is = Messages.class.getResourceAsStream("/messages_" + langCode + ".properties");
                    Reader bufferedReader = new InputStreamReader(is, "UTF-8");
                    properties.load(bufferedReader);
                    messages.put(langCode, properties);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return properties.getProperty(key, key);
        }
        return "";
    }

    public static String get(String key) {
        return get(key, systemParameterService.getSystemParameterValue("systemLanguageCode", "KA"));
    }
}
