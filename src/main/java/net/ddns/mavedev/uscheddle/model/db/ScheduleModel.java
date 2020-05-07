package net.ddns.mavedev.uscheddle.model.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schedules")
public class ScheduleModel {

    @Id
    private String id = "";
    private String ownerId = "";
    private String name = "";
    private String[] mon = new String[6];
    private String[] tue = new String[6];
    private String[] wed = new String[6];
    private String[] thu = new String[6];
    private String[] fri = new String[6];
    private String[] sat = new String[6];

    public ScheduleModel(final String id, final String ownerId, final String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

}
