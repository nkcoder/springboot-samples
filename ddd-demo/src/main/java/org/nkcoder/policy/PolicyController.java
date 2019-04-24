package org.nkcoder.policy;

import io.swagger.annotations.ApiOperation;
import org.nkcoder.policy.command.CreateCarPolicyCommand;
import org.nkcoder.policy.command.CreateHomePolicyCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private PolicyApplicationService policyApplicationService;

    public PolicyController(PolicyApplicationService policyApplicationService) {
        this.policyApplicationService = policyApplicationService;
    }

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
