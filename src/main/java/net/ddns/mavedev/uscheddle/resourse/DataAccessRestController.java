package net.ddns.mavedev.uscheddle.resourse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;
import net.ddns.mavedev.uscheddle.model.request.GenerateRequestModel;
import net.ddns.mavedev.uscheddle.model.response.ResponseModel;
import net.ddns.mavedev.uscheddle.repository.SchedulesRepository;

@RestController
@CrossOrigin
public class DataAccessRestController {

    @Autowired
    private SchedulesRepository db;

    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseModel> getSchedule(
            @PathVariable(value = "id") final String id) {
        ScheduleModel schedule = db.findById(id).get();
        if (schedule == null) {
            // return ResponseEntity.status(HttpStatus.NO_CONTENT)
            // .body(new ResponseModel(null, false));
        } else {
            // return ResponseEntity.ok(new ResponseModel(schedule, true));
        }
        return null;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ResponseModel> generate(
            @RequestBody final GenerateRequestModel request) {
        if (!request.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel(null, false));
        }

        ResponseModel response = processGenerateRequest(request);
        if (response.getSchedule() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel(null, false));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    private ResponseModel processGenerateRequest(final GenerateRequestModel request) {
        String generatedId = getIdBasedOnCurrentTime();
        ScheduleModel toBeSaved = new ScheduleModel(generatedId, "testOwner", "testName");
        db.save(toBeSaved);
        return new ResponseModel(toBeSaved, true);
    }

    private String getIdBasedOnCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMdd-HHmm-ssSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
