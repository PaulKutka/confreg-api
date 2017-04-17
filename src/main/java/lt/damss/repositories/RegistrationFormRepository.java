package lt.damss.repositories;

import lt.damss.models.RegistrationForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by paulius on 17.3.12.
 */

@Repository
public interface RegistrationFormRepository extends CrudRepository<RegistrationForm, Long>{
    RegistrationForm findByUniqueCode(String uniqueCode);

}
