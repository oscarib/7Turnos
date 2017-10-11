package es.edm.domain.middle;

import java.util.HashMap;
import java.util.Map;

public class Properties {

    Map<String, String> properties;

    public Properties() {
        properties = new HashMap<String, String>();
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }
}
