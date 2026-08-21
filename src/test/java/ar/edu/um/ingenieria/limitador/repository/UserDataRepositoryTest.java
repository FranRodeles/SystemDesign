package ar.edu.um.ingenieria.limitador.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import ar.edu.um.ingenieria.limitador.domain.UserData;

@DataJpaTest
class UserDataRepositoryTest {

    @Autowired
    private UserDataRepository userDataRepository;

    @Test
    void shouldSaveAndFindUserDataById() {
        var userData = new UserData();
        userData.setFirstName("Juan");
        userData.setLastName("Perez");
        userData.setAddress("Calle Falsa 123");
        userData.setPhoneNumber("123456789");

        var saved = userDataRepository.save(userData);

        Optional<UserData> found = userDataRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Juan");
        assertThat(found.get().getLastName()).isEqualTo("Perez");
        assertThat(found.get().getPhoneNumber()).isEqualTo("123456789");
    }

    @Test
    void shouldFindAllUserData() {
        var ud1 = new UserData();
        ud1.setFirstName("Juan");
        ud1.setPhoneNumber("111");

        var ud2 = new UserData();
        ud2.setFirstName("Maria");
        ud2.setPhoneNumber("222");

        userDataRepository.save(ud1);
        userDataRepository.save(ud2);

        List<UserData> all = userDataRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void shouldDeleteUserData() {
        var userData = new UserData();
        userData.setFirstName("To Delete");
        userData.setPhoneNumber("999");

        var saved = userDataRepository.save(userData);
        Long id = saved.getId();

        userDataRepository.deleteById(id);

        assertThat(userDataRepository.findById(id)).isEmpty();
    }
}
