package plugins.Fofa.DataType;

import java.util.HashMap;
import java.util.Map;

public class Config {
    String email;
    String key;
    Map<String, Advanced> advanceds = new HashMap<>();
    String outPath;

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public Map<String, Advanced> getAdvanceds() {
        return advanceds;
    }

    public void setAdvanceds(Map<String, Advanced> advanceds) {
        this.advanceds = advanceds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
