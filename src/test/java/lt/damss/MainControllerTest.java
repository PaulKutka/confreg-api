package lt.damss;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.damss.controller.MainController;
import lt.damss.models.RegistrationForm;
import lt.damss.service.RegistrationService;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfregApiApplication.class)
@WebAppConfiguration
public class MainControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private List<RegistrationForm> formList = new ArrayList<>();

    @InjectMocks
    private MainController mainController;

    @Mock
    private RegistrationService registrationService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).addFilters(new CorsFilter()).build();


        this.mapper = new ObjectMapper();


        RegistrationForm firstForm = new RegistrationForm();
        firstForm.setFirstName("Vardas");
        firstForm.setLastName("Pavarde");
        firstForm.setEmail("test@test");

        RegistrationForm secondForm = new RegistrationForm();
        secondForm.setFirstName("Vardenis");
        secondForm.setLastName("Pavardenis");
        secondForm.setEmail("testas@testas");

        Mockito.when(registrationService.registerForm(firstForm)).thenReturn(firstForm);
        Mockito.when(registrationService.registerForm(secondForm)).thenReturn(secondForm);

        RegistrationForm registrationForms[] = {firstForm, secondForm};

//		Iterator<RegistrationForm> mockIterator = mock(Iterator.class);
        Iterable<RegistrationForm> iterable = Arrays.asList(registrationForms);
//
        Mockito.when(registrationService.getAllForms()).thenReturn(iterable);


        //Iterable<RegistrationForm> iterableAnswer = registrationService.getAllForms();

        this.formList.add(registrationService.registerForm(firstForm));
        this.formList.add(registrationService.registerForm(secondForm));
    }

    @Test
    public void readForms() throws Exception {
        RegistrationForm firstForm = new RegistrationForm();
        firstForm.setFirstName("Vardas");
        firstForm.setLastName("Pavarde");
        firstForm.setEmail("test@test");

        RegistrationForm secondForm = new RegistrationForm();
        secondForm.setFirstName("Vardenis");
        secondForm.setLastName("Pavardenis");
        secondForm.setEmail("testas@testas");

        RegistrationForm registrationForms[] = {firstForm, secondForm};

        Iterable<RegistrationForm> iterable = Arrays.asList(registrationForms);

        Mockito.when(registrationService.getAllForms()).thenReturn(iterable);

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Vardas")))
                .andExpect(jsonPath("$[0].lastName", is("Pavarde")))
                .andExpect(jsonPath("$[1].firstName", is("Vardenis")))
                .andExpect(jsonPath("$[1].lastName", is("Pavardenis")));


//		TODO Write full test of an object
    }


    @Test
    public void createForm() throws Exception {

        RegistrationForm newForm = new RegistrationForm();
        newForm.setFirstName("Vardas");
        newForm.setLastName("Pavarde");
        newForm.setEmail("test@test");

//		TODO Write full test of an object


        Mockito.when(registrationService.registerForm(any())).thenReturn(newForm);

        String bookmarkJson = mapper.writeValueAsString(newForm);

        String expectedJson = mapper.writeValueAsString(newForm);

        this.mockMvc.perform(post("/post")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));


    }

    @Test
    public void findFormByUniqueId() throws Exception {
        RegistrationForm newForm = new RegistrationForm();
        newForm.setFirstName("Vardas");
        newForm.setLastName("Pavarde");
        newForm.setEmail("test@test");

        Mockito.when(registrationService.findByUniqueCode(any())).thenReturn(newForm);


        String uniqueCode = "Valid code";

        String expectedJson = mapper.writeValueAsString(newForm);

        this.mockMvc.perform(post("/find")
                .contentType(contentType)
                .content(uniqueCode))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));


    }
}