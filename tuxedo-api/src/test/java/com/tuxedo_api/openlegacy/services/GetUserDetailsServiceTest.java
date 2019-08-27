package com.tuxedo_api.openlegacy.services;


import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuxedo_api.openlegacy.config.AbstractWebContextAwareTest;
import com.tuxedo_api.openlegacy.services.GetUserDetailsService.GetUserDetailsIn;
import com.tuxedo_api.openlegacy.services.GetUserDetailsService.GetUserDetailsOut;


/**
 *  A test which invokes GetUserDetails service.
 *  To run the test, select Run As -> JUnit test.
 *  If the service has parameters, they should be set via the test.
 */
public class GetUserDetailsServiceTest extends AbstractWebContextAwareTest {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDetailsServiceTest.class);
    
    @Inject
    GetUserDetailsService getUserDetailsService;

    @Test
    public void testGetUserDetailsService() throws Exception {
        long before = System.currentTimeMillis();

        GetUserDetailsIn getUserDetailsIn = new GetUserDetailsIn();
        getUserDetailsIn.setId3(0);
        GetUserDetailsOut getUserDetailsOut = getUserDetailsService.getGetUserDetails(getUserDetailsIn);
        Assert.assertNotNull(getUserDetailsOut);

        long after = System.currentTimeMillis();
        logger.info("Execution time:" + (after - before));
    }
}
