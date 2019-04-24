package org.nkcoder.user.domain.service;

import org.nkcoder.user.UserRepository;
import org.nkcoder.user.domain.model.User;
import org.nkcoder.user.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotExistException::new);
        return user.isPassWordCorrect(password);
    }
}
