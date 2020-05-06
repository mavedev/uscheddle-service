package net.ddns.mavedev.uscheddle.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomModel {

    @JsonProperty("classroomNumber")
    private String number;

    @JsonProperty("classroomType")
    private String type;

    public boolean isValid() {
        return this.number != null && this.type != null;
    }
}
