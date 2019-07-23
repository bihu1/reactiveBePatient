package com.bihuniak.piotr.reactiveBePatient.domain.user;

import com.bihuniak.piotr.reactiveBePatient.domain.user.http.model.ChangePasswordRequest;
import com.bihuniak.piotr.reactiveBePatient.domain.user.http.model.ChangeUsernameRequest;
import com.bihuniak.piotr.reactiveBePatient.domain.user.http.model.UserView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PutMapping("/password/changing")
    @ApiOperation(value = "Change password", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Updated"),
            @ApiResponse(code = 400, message = "Bad old password")
    })
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        //userService.changePassword(changePasswordRequest);
    }

    @PutMapping("/username/changing")
    @ApiOperation(value = "Change username", authorizations = {@Authorization("Bearer <oAuth2>")} )
    @ApiResponses({
            @ApiResponse(code = 204, message = "OK"),
            @ApiResponse(code = 400, message = "Bad old password")
    })
    @ResponseStatus(HttpStatus.OK)
    public void changeUsername(@RequestBody @Valid ChangeUsernameRequest changeUsernameRequest) {
        //userService.changeUsername(changeUsernameRequest);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Info about me", authorizations = {@Authorization("Bearer <oAuth2>")} )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void/*UserView*/ currentUser(/*@ApiIgnore @AuthenticationPrincipal SecurityUserDetails userDetails*/){
        //return userService.getMe(userDetails);
    }
}
