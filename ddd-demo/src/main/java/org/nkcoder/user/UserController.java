package org.nkcoder.user;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.nkcoder.user.command.RegisterCommand;
import org.nkcoder.user.command.SetPasswordCommand;
import org.nkcoder.user.command.UserLoginCommand;
import org.nkcoder.user.exception.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Api(value = "User REST API", description = "User related API")
public class UserController {

  @Autowired
  private UserApplicationService userApplicationService;

  @CrossOrigin
  @PutMapping(value = "/register")
  @ResponseStatus(CREATED)
  @ApiOperation(value = "POST", notes = "Registration")
  public String register(@RequestBody @Valid RegisterCommand command) {
    return userApplicationService.register(command);
  }

  @CrossOrigin
  @PutMapping(value = "/password")
  @ResponseStatus(ACCEPTED)
  @ApiOperation(value = "PUT", notes = "Set password")
  public void setPassword(@RequestBody @Valid SetPasswordCommand command) {
    userApplicationService.initialPassword(command);
  }

  @CrossOrigin
  @PostMapping(value = "/login")
  @ResponseStatus(FOUND)
  @ApiOperation(value = "POST", notes = "Login")
  public void login(@RequestBody @Valid UserLoginCommand command) {
    if (!userApplicationService.login(command)) {
      throw new LoginFailedException();
    }
  }
}
