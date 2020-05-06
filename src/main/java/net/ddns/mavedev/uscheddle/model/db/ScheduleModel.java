package net.ddns.mavedev.uscheddle.model.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(collection = "schedules")
public class ScheduleModel {

    @Id
    private int id;
    private String testField;

}
