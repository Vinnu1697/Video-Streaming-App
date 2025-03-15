package com.eeze.videostreaming.controller;

/*
 * @author : Vinit Udawant
 */


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Video Streaming Application is Running");
    }

}
