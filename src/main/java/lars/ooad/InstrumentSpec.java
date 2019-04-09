package lars.ooad;

import java.util.HashMap;
import java.util.Map;

class InstrumentSpec {

    private Map<String, Object> properties;

    public InstrumentSpec(Map<String, Object> properties) {
        if (properties == null) {
            this.properties = new HashMap<>();
        } else {
            this.properties = new HashMap<>(properties);
        }
    }

    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public Map getProperties() {
        return properties;
    }

    public boolean matches(InstrumentSpec otherSpec) {
        for (Object o : otherSpec.getProperties().keySet()) {
            String propertyName = (String) o;
            if (!properties.get(propertyName).equals(
                    otherSpec.getProperty(propertyName))) {
                return false;
            }
        }
        return true;
    }
}


