package com.bihuniak.piotr.reactiveBePatient.domain.token;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("token")
    @ApiOperation(value = "Get token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public void postAccessToken(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
     ){
        response.sendRedirect("oauth/token?username="+username+"&password="+password+"&grant_type=password");
    }
}
