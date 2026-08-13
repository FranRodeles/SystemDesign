package ar.edu.um.ingenieria.limitador.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class UserTest {

    @Test
    void shouldCreateUserWithRoleAndProfileData() {
        var role = new Role();
        role.setDescription("ADMIN");
        var user = new User();
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setActivated(true);
        user.setRole(role);
        

        assertThat(user.getUsername()).isEqualTo("jdoe");
        assertThat(user.getEmail()).isEqualTo("jdoe@example.com");
        assertThat(user.getActivated()).isTrue();
        assertThat(user.getRole()).isSameAs(role);
        assertThat(role.getDescription()).isEqualTo("ADMIN");
    }
}
