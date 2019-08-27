package tests;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlegacy.core.rpc.RpcSession;
import org.openlegacy.core.rpc.actions.RpcActions;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;


import com.tuxedo_sdk.openlegacy.Getuserdetails;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"getuserdetails-test"})
public class GetuserdetailsTest {

    @Inject
    private RpcSession rpcSession;

    @Test
    public void testGetuserdetailsEXECUTE() throws Exception {
        try {
            Getuserdetails getuserdetails = new Getuserdetails();
            getuserdetails.setId3(101);
            getuserdetails = rpcSession.doAction(RpcActions.EXECUTE(), getuserdetails);
            assertNotNull(getuserdetails);
        } finally {
            rpcSession.disconnect();
        }
    }

}

