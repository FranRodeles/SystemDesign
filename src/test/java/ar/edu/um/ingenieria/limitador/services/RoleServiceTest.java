package ar.edu.um.ingenieria.limitador.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.um.ingenieria.limitador.domain.Role;
import ar.edu.um.ingenieria.limitador.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void shouldReturnAllRoles() {
        var role1 = new Role(1L, "Admin", "ROLE_ADMIN", null);
        var role2 = new Role(2L, "User", "ROLE_USER", null);
        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));

        List<Role> roles = roleService.findAll();

        assertThat(roles).hasSize(2);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnRoleById() {
        var role = new Role(1L, "Admin", "ROLE_ADMIN", null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<Role> found = roleService.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getRoleName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void shouldReturnEmptyWhenRoleNotFound() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Role> found = roleService.findById(99L);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldSaveRole() {
        var role = new Role(null, "Editor", "ROLE_EDITOR", null);
        var saved = new Role(1L, "Editor", "ROLE_EDITOR", null);
        when(roleRepository.save(any(Role.class))).thenReturn(saved);

        Role result = roleService.save(role);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRoleName()).isEqualTo("ROLE_EDITOR");
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void shouldUpdateExistingRole() {
        var existing = new Role(1L, "Old Desc", "ROLE_OLD", null);
        var updated = new Role(1L, "New Desc", "ROLE_NEW", null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenReturn(updated);

        Role result = roleService.update(1L, updated);

        assertThat(result.getDescription()).isEqualTo("New Desc");
        assertThat(result.getRoleName()).isEqualTo("ROLE_NEW");
        verify(roleRepository, times(1)).save(updated);
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentRole() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());
        var role = new Role(99L, "Desc", "ROLE_X", null);

        assertThatThrownBy(() -> roleService.update(99L, role))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Role not found");

        verify(roleRepository, never()).save(any());
    }

    @Test
    void shouldDeleteRoleById() {
        var role = new Role(1L, "Admin", "ROLE_ADMIN", null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        roleService.deleteById(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentRole() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roleService.deleteById(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Role not found");

        verify(roleRepository, never()).deleteById(any());
    }
}
