package com.tuxedo_api.openlegacy.services.controllers;

import javax.inject.Inject;

import org.openlegacy.core.annotations.OpenlegacyDesigntime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuxedo_api.openlegacy.services.GetUserDetailsService;
import com.tuxedo_api.openlegacy.services.GetUserDetailsService.GetUserDetailsIn;
import com.tuxedo_api.openlegacy.services.GetUserDetailsService.GetUserDetailsOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

@RestController
@RequestMapping(path = "/api/getuserdetails", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value="GetUserDetails")
@OpenlegacyDesigntime(editor = "WebServiceEditor")
public class GetUserDetailsController {

    @Inject
    private GetUserDetailsService getUserDetailsService;

    @PostMapping
    @ApiOperation(value="GetUserDetails GET operation", response = GetUserDetailsOut.class, 
        authorizations = { @Authorization(value = "oauth2-password", scopes = { @AuthorizationScope(scope = "test", description = "Test") }) , @Authorization(value = "oauth2", scopes = { @AuthorizationScope(scope = "test", description = "Test") }) })
    public GetUserDetailsOut getGetUserDetails(@RequestBody GetUserDetailsIn getUserDetailsIn) throws Exception {
        return getUserDetailsService.getGetUserDetails(getUserDetailsIn);
    }
}