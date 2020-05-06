package net.ddns.mavedev.uscheddle.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {

    @JsonProperty("scheduleId")
    private String scheduleId;

    @JsonProperty("editable")
    private boolean editable;

}
