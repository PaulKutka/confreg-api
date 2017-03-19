package lt.damss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


/**
 * Created by paulius on 17.3.11.
 */
@Configuration
public class MyApplicationConfiguration {
    @Bean
    @Profile("development")
    public static PropertySourcesPlaceholderConfigurer developmentPropertyPlaceholderConfigurer() {
        String activeProfile = System.getProperty("spring.profiles.active", "production");
        String propertiesFilename = "app-" + activeProfile + ".properties";

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource(propertiesFilename));

        return configurer;
    }




}
