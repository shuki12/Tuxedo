package com.tuxedo_api.openlegacy.services;


import org.openlegacy.core.annotations.OpenlegacyDesigntime;
import org.openlegacy.core.annotations.services.Service;

import com.tuxedo_sdk.openlegacy.Getuserdetails;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *  A service interface and input/output definition for a web service.
 *  Defines the contract between the client and server. The client uses the same interface for testing the service via Java code. 
 *  The interface GetUserDetailsService can be customized to enabling passing parameters to the service.     
 */

@Service(name = "GetUserDetails")
@OpenlegacyDesigntime(editor = "WebServiceEditor")
public interface GetUserDetailsService {

    public GetUserDetailsOut getGetUserDetails(GetUserDetailsIn getUserDetailsIn) throws Exception;

    @Getter
    @Setter
    public static class GetUserDetailsIn {
        
        Integer id3;
    }
    
    @ApiModel(value="GetUserDetailsOut", description="")
    @Getter
    @Setter
    public static class GetUserDetailsOut {
        
        @ApiModelProperty(value="Getuserdetails")
        Getuserdetails getuserdetails;
    }
}
