package net.ddns.mavedev.uscheddle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.mavedev.uscheddle.model.request.GenerateRequestModel;

@RestController
@CrossOrigin
public class DataAccessRestController {
    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getSchedule(@PathVariable(value = "id") final String id) {
        return new HashMap<String, String>() {
            private static final long serialVersionUID = 5276836687399797368L;
            {
                put("testKey1", "testValue1");
                put("testKey2", "testValue2");
                put("testKey3", "testValue3");
            }
        };
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GenerateRequestModel generate(@RequestBody final GenerateRequestModel request) {
        return request;
    }
}