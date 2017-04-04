package lt.damss;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import lt.damss.service.NotificationService;
import lt.damss.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulius on 17.4.3.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfregApiApplication.class)
@WebAppConfiguration
public class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    private RegistrationFormRepository registrationFormRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

//    @Test
//    public void getAllForms() throws Exception {
//        RegistrationForm firstForm = new RegistrationForm();
//        firstForm.setFirstName("Vardas");
//        firstForm.setLastName("Pavarde");
//        firstForm.setEmail("test@test");
//
//        RegistrationForm secondForm = new RegistrationForm();
//        secondForm.setFirstName("Vardenis");
//        secondForm.setLastName("Pavardenis");
//        secondForm.setEmail("testas@testas");
//
//        registrationFormRepository.save(firstForm);
//        registrationFormRepository.save(secondForm);
//
//     //   Mockito.when(registrationFormRepository.findAll()).thenCallRealMethod();
//
//        RegistrationForm registrationForms[] = {firstForm, secondForm};
//
//       Iterable<RegistrationForm> iterable = Arrays.asList(registrationForms);
//
//
//
//        Iterable<RegistrationForm> result =  registrationService.getAllForms();
//
//        assertThat(result,    IsIterableContainingInOrder.contains(iterable));
//
//      TODO Parasyti testa get All'ui
//    }

    @Test
    public void getItemByUniqueId() throws Exception {
        RegistrationForm firstForm = new RegistrationForm();
        firstForm.setFirstName("Vardas");
        firstForm.setLastName("Pavarde");
        firstForm.setEmail("test@test");

        RegistrationForm form = registrationFormRepository.save(firstForm);
        String uniqueId = form.getUniqueCode();

        assertEquals(registrationService.findByUniqueCode(uniqueId).getFirstName(), form.getFirstName());

    }


}
