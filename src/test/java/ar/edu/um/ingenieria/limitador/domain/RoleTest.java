package ar.edu.um.ingenieria.limitador.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void shouldCreateRoleWithDescriptionAndName() {
        var role = new Role();
        role.setDescription("MANAGER");
        role.setRoleName("ROLE_ADMIN");
        assertThat(role.getDescription()).isEqualTo("MANAGER");
        assertThat(role.getRoleName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void shouldCreateRoleWithMultipleUsers() {
        var role = new Role();
        role.setDescription("ADMIN");
        role.setRoleName("ROLE_ADMIN");

        var user1 = new User();
        user1.setUsername("jdoe");
        user1.setEmail("jdoe@example.com");

        var user2 = new User();
        user2.setUsername("jane");
        user2.setEmail("jane@example.com");

        role.setUsers(Set.of(user1, user2));

        assertThat(role.getUsers()).hasSize(2);
        assertThat(role.getUsers()).containsExactlyInAnyOrder(user1, user2);
    }
}
