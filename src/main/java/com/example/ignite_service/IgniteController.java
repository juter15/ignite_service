package com.example.ignite_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IgniteController {
    //private final IgniteService igniteService;
    @GetMapping("/create")
    public ResponseEntity getOrCreateCache(
            @RequestParam String cacheName) {

        //igniteService.createCache(cacheName, UserModel.class);

        return ResponseEntity.ok(true);
    }
}
