package ar.edu.um.ingenieria.limitador.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import ar.edu.um.ingenieria.limitador.domain.Role;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldSaveAndFindRoleById() {
        var role = new Role();
        role.setDescription("Administrator");
        role.setRoleName("ROLE_ADMIN");

        var saved = roleRepository.save(role);

        Optional<Role> found = roleRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRoleName()).isEqualTo("ROLE_ADMIN");
        assertThat(found.get().getDescription()).isEqualTo("Administrator");
    }

    @Test
    void shouldFindAllRoles() {
        var role1 = new Role();
        role1.setDescription("Admin");
        role1.setRoleName("ROLE_ADMIN");

        var role2 = new Role();
        role2.setDescription("User");
        role2.setRoleName("ROLE_USER");

        roleRepository.save(role1);
        roleRepository.save(role2);

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(2);
    }

    @Test
    void shouldDeleteRole() {
        var role = new Role();
        role.setDescription("To Delete");
        role.setRoleName("ROLE_DELETE");

        var saved = roleRepository.save(role);
        Long id = saved.getId();

        roleRepository.deleteById(id);

        assertThat(roleRepository.findById(id)).isEmpty();
    }
}
