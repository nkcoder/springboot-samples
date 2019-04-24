package org.nkcoder.user;

import org.nkcoder.user.command.RegisterCommand;
import org.nkcoder.user.command.SetPasswordCommand;
import org.nkcoder.user.command.UserLoginCommand;
import org.nkcoder.user.domain.model.User;
import org.nkcoder.user.domain.service.LoginService;
import org.nkcoder.user.domain.service.RegisterService;
import org.nkcoder.user.exception.UserNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private static Logger logger = LoggerFactory.getLogger(UserApplicationService.class);

    private UserRepository userRepository;
    private LoginService loginService;
    private RegisterService registerService;

    public UserApplicationService(UserRepository userRepository,
                                  LoginService loginService,
                                  RegisterService registerService) {
        this.userRepository = userRepository;
        this.loginService = loginService;
        this.registerService = registerService;
    }

    public String register(RegisterCommand command) {
        User user = registerService.createUser(command.getOwnerEmail(), command.getPolicyNumber());
        logger.info("Create user with email [{}]", command.getOwnerEmail());
        return user.getUuid();
    }

    public void initialPassword(SetPasswordCommand command) {
        User user = userRepository.findByUuid(command.getUuid())
                .orElseThrow(UserNotExistException::new);
        user.setPassWord(command.getPassword());
        logger.info("Set password with uuid[{}]", command.getUuid());
        userRepository.save(user);
    }

    public boolean login(UserLoginCommand command) {
        boolean loginSuccess = loginService.login(command.getEmail(), command.getPassword());
        logger.info("User login with email [{}]", command.getEmail());
        return loginSuccess;
    }
}
