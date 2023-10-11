package plugins.Fofa.DataType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Advanced implements Serializable {
    private boolean status; // 是否启用的状态
    private String value;   // 选项的值
    private String name;    // 选项的名字，即展示的名字
    private String otherValue;  // 其他值，也可以是隐藏值，不做任何展示，但是负责记录一些东西，比如 nextID
    Map<String, Advanced> advanceds = new HashMap<>();  // 高级

    public Map<String, Advanced> getAdvanceds() {
        return advanceds;
    }

    public void setAdvanceds(Map<String, Advanced> advanceds) {
        this.advanceds = advanceds;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
