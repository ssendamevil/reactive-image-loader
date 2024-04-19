package com.erg.prometheusk8s;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/data")
    public String getData(){
        return "All data returned";
    }

    @PostMapping("/data")
    public String saveData(@RequestParam(name = "data") String data) {
        return "Data: " + data +" stored successfully!";
    }
}
