package lt.damss;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfregApiApplication.class)
@WebAppConfiguration
public class MainControllerTest {


	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;

	private String userName = "bdussault";

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private RegistrationForm form;

	private List<RegistrationForm> formList = new ArrayList<>();

	@Autowired
	private RegistrationFormRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.repository.deleteAll();

		RegistrationForm firstForm = new RegistrationForm();
		firstForm.setFirstName("Vardas");
		firstForm.setLastName("Pavarde");
		firstForm.setEmail("test@test");

		RegistrationForm secondForm = new RegistrationForm();
		secondForm.setFirstName("Vardenis");
		secondForm.setLastName("Pavardenis");
		secondForm.setEmail("testas@testas");

		this.formList.add(repository.save(firstForm));
		this.formList.add(repository.save(secondForm));
	}

	@Test
	public void readForms() throws Exception {
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

		String bookmarkJson = json(newForm);

		String expectedJson = json(newForm);

		this.mockMvc.perform(post("/post")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}