package com.iotcloud.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iot/api/v2")
public class ApiFrontController {

    @RequestMapping("/devices")
    public String getDecives() {
        return null;
    }

    @PostMapping("/devices")
    public String setDecive() {
        return null;
    }

    @RequestMapping("/devices/{deviceId}")
    public String getDecives(String deviceId) {
        return null;
    }

    @PutMapping("/devices/{deviceId}")
    public String putDecives(String deviceId) {
        return null;
    }
}
