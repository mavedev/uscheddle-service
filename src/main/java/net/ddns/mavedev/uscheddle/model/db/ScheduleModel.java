package net.ddns.mavedev.uscheddle.model.db;

import java.util.Objects;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private String id;
    @JsonProperty("ownerId")
    private String ownerId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mon")
    private String[][] mon;
    @JsonProperty("tue")
    private String[][] tue;
    @JsonProperty("wed")
    private String[][] wed;
    @JsonProperty("thu")
    private String[][] thu;
    @JsonProperty("fri")
    private String[][] fri;
    @JsonProperty("sat")
    private String[][] sat;

    public ScheduleModel(final String id, final String ownerId, final String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public boolean isValid() {
        return Stream.of(id, name, mon, tue, wed, thu, fri, sat).noneMatch(Objects::isNull);
    }

}
