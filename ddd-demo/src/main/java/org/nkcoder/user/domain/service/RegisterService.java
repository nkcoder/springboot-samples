package org.nkcoder.user.domain.service;

import org.nkcoder.exception.ErrorInputException;
import org.nkcoder.policy.domain.model.Policy;
import org.nkcoder.policy.exception.PolicyNotExistException;
import org.nkcoder.policy.repository.PolicyRepository;
import org.nkcoder.user.UserRepository;
import org.nkcoder.user.domain.model.User;
import org.nkcoder.user.exception.EmailExistException;
import org.nkcoder.utils.EmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    EmailManager emailManager;

    public User createUser(String email, String policyNumber) {
        if (!isInputValidation(policyNumber, email)) {
            throw new ErrorInputException();
        }
        if (isEmailExist(email)) {
            throw new EmailExistException();
        }
        String uuid = UUID.randomUUID().toString();
        User user = new User(uuid, null, email);
        userRepository.save(user);
        emailManager.sendEmail(uuid);
        return user;
    }

    private boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean isInputValidation(String policyNumber, String email) {
        Policy policy = policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(PolicyNotExistException::new);
        return policy.getPolicyHolder().getEmail().equals(email) && !email.equals("") && !policyNumber
                .equals("");
    }
}
