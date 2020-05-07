package net.ddns.mavedev.uscheddle.resourse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
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
import net.ddns.mavedev.uscheddle.model.request.create.GenerateRequestModel;
import net.ddns.mavedev.uscheddle.model.request.delete.DeleteRequestModel;
import net.ddns.mavedev.uscheddle.model.request.read.ReadRequestModel;
import net.ddns.mavedev.uscheddle.model.request.update.UpdateRequestModel;
import net.ddns.mavedev.uscheddle.model.response.ResponseModel;
import net.ddns.mavedev.uscheddle.repository.SchedulesRepository;

@RestController
@CrossOrigin
public class DataAccessRestController {

    @Autowired
    private SchedulesRepository db;

    @RequestMapping(value = "/generate", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ResponseModel> create(
            @RequestBody final GenerateRequestModel request) {
        if (!request.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.empty());
        }

        ResponseModel response = processGenerateRequest(request);
        if (!response.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.empty());
        } else {
            return ResponseEntity.ok(response.editable());
        }
    }

    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ResponseModel> read(@RequestBody ReadRequestModel request,
            @PathVariable(value = "id") final String id) {
        ScheduleModel schedule = null;
        try {
            schedule = db.findById(id).get();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseModel.empty());
        }

        ResponseModel response = ResponseModel.fromScheduleModel(schedule);
        return schedule.getOwnerId().equals(request.getSenderId())
                ? ResponseEntity.ok(response.editable())
                : ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ResponseModel> update(
            @RequestBody final UpdateRequestModel request,
            @PathVariable(value = "id") final String id) {
        if (request != null) {
            request.getSchedule().setId(id);
        }
        if (!request.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.empty());
        }

        ResponseModel response = null;
        System.out.println(request.getSchedule());
        try {
            response = processUpdateRequest(request);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseModel.empty());
        } catch (SecurityException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseModel.empty());
        }

        if (!response.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.empty());
        } else {
            return ResponseEntity.ok(response.editable());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ResponseModel> delete(
            @RequestBody final DeleteRequestModel request,
            @PathVariable(value = "id") final String id) {
        ScheduleModel schedule = null;
        try {
            schedule = db.findById(id).get();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseModel.empty());
        }

        if (!schedule.getOwnerId().equals(request.getSenderId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseModel.empty());
        }

        db.delete(schedule);
        return ResponseEntity.ok(null);
    }

    private ResponseModel processGenerateRequest(final GenerateRequestModel request) {
        ScheduleModel toBeSaved = getDummySchedule();
        db.save(toBeSaved);
        return ResponseModel.fromScheduleModel(toBeSaved);
    }

    private ResponseModel processUpdateRequest(final UpdateRequestModel request)
            throws SecurityException, NoSuchElementException {
        final ScheduleModel updatedModel = request.getSchedule();
        ScheduleModel toBeUpdated = db.findById(updatedModel.getId()).get();

        if (!request.getSenderId().equals(toBeUpdated.getOwnerId())) {
            throw new SecurityException();
        }

        updatedModel.setOwnerId(toBeUpdated.getOwnerId());
        ScheduleModel updatedResult = db.save(updatedModel);
        return ResponseModel.fromScheduleModel(updatedResult);
    }

    private String getIdBasedOnCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMdd-HHmm-ssSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private ScheduleModel getDummySchedule() {
        String generatedId = getIdBasedOnCurrentTime();
        return new ScheduleModel(generatedId, "dummyOwner", "dummyName");
    }
}
