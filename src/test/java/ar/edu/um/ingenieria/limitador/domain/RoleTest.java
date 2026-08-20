package ar.edu.um.ingenieria.limitador.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void shouldCreateRoleWithDescription() {
        var role = new Role();
        role.setDescription("MANAGER");

        assertThat(role.getDescription()).isEqualTo("MANAGER");
    }
}
