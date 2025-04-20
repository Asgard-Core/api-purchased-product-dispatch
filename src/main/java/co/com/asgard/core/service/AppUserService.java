package co.com.asgard.core.service;

import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }
}
