
package ru.terra.lorview.dto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EditSummary {

    private String editNick;
    private String editDate;
    private Long editCount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEditNick() {
        return editNick;
    }

    public void setEditNick(String editNick) {
        this.editNick = editNick;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public Long getEditCount() {
        return editCount;
    }

    public void setEditCount(Long editCount) {
        this.editCount = editCount;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
