package net.ddns.mavedev.uscheddle.model.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestModel {

    @JsonProperty("updatedSchedule")
    private ScheduleModel schedule;

    @JsonProperty("senderId")
    private String senderId;

}
