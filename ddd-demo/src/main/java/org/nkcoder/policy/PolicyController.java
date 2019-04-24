package org.nkcoder.policy;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.nkcoder.policy.command.CreateCarPolicyCommand;
import org.nkcoder.policy.command.CreateHomePolicyCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    @Autowired
    private PolicyApplicationService policyApplicationService;

    @PutMapping(value = "/home")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "PUT", notes = "Create home policy")
    public String createHomePolicy(@RequestBody @Valid CreateHomePolicyCommand command) {
        return policyApplicationService.createPolicy(command);
    }

    @PutMapping(value = "/car")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "PUT", notes = "Create car policy")
    public String createCarPolicy(@RequestBody @Valid CreateCarPolicyCommand command) {
        return policyApplicationService.createPolicy(command);
    }
}
