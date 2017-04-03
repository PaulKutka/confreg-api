package lt.damss;

import lt.damss.service.RegistrationService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by paulius on 17.4.3.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfregApiApplication.class)
@WebAppConfiguration
public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;
    


}
