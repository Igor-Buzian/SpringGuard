package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SitePageController {
    @GetMapping("/user")
    public String user(){
        return "user";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
