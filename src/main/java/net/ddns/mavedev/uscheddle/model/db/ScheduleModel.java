package net.ddns.mavedev.uscheddle.model.db;

import java.util.Objects;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private static final String[][] DUMMY_ARRAY = new String[0][0];

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("ownerId")
    private String ownerId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mon")
    private String[][] mon = DUMMY_ARRAY;
    @JsonProperty("tue")
    private String[][] tue = DUMMY_ARRAY;
    @JsonProperty("wed")
    private String[][] wed = DUMMY_ARRAY;
    @JsonProperty("thu")
    private String[][] thu = DUMMY_ARRAY;
    @JsonProperty("fri")
    private String[][] fri = DUMMY_ARRAY;
    @JsonProperty("sat")
    private String[][] sat = DUMMY_ARRAY;

    public ScheduleModel(final String id, final String ownerId, final String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public boolean isValid() {
        return Stream.of(id, name, mon, tue, wed, thu, fri, sat).noneMatch(Objects::isNull);
    }

}
