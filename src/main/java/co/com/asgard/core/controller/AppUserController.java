package co.com.asgard.core.controller;

import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
}

