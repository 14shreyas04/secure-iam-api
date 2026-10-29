
package com.Java.Project.controller;

import com.Java.Project.model.clients;
import com.Java.Project.service.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private clientService ser;

    @GetMapping("/dashboard")
    public Map<String, String> dashboard() {
        return Map.of(
                "message", "Welcome to the Admin Dashboard",
                "access", "ADMIN"
        );
    }

    @GetMapping("/users")
    public List<clients> getusers(){
        return ser.getall();
    }
}
