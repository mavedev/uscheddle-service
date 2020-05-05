package net.ddns.mavedev.uscheddle;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {
    @GetMapping("/")
    public String test() {
        return "<h1>Hello</h1>";
    }
}