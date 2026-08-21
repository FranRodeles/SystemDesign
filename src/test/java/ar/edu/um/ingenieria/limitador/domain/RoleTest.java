package ar.edu.um.ingenieria.limitador.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}
