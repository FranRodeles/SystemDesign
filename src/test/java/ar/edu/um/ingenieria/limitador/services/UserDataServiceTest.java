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

import ar.edu.um.ingenieria.limitador.domain.UserData;
import ar.edu.um.ingenieria.limitador.repository.UserDataRepository;

@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {

    @Mock
    private UserDataRepository userDataRepository;

    @InjectMocks
    private UserDataServiceImpl userDataService;

    @Test
    void shouldReturnAllUserData() {
        var ud1 = new UserData(1L, "Juan", "Perez", "Calle 1", "111", null);
        var ud2 = new UserData(2L, "Maria", "Lopez", "Calle 2", "222", null);
        when(userDataRepository.findAll()).thenReturn(List.of(ud1, ud2));

        List<UserData> result = userDataService.findAll();

        assertThat(result).hasSize(2);
        verify(userDataRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserDataById() {
        var ud = new UserData(1L, "Juan", "Perez", "Calle 1", "111", null);
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(ud));

        Optional<UserData> found = userDataService.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Juan");
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        when(userDataRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserData> found = userDataService.findById(99L);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldSaveUserData() {
        var ud = new UserData(null, "Carlos", null, null, "333", null);
        var saved = new UserData(1L, "Carlos", null, null, "333", null);
        when(userDataRepository.save(any(UserData.class))).thenReturn(saved);

        UserData result = userDataService.save(ud);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Carlos");
    }

    @Test
    void shouldUpdateExistingUserData() {
        var existing = new UserData(1L, "Old", null, null, "111", null);
        var updated = new UserData(1L, "New", "Updated", "Address", "222", null);
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userDataRepository.save(any(UserData.class))).thenReturn(updated);

        UserData result = userDataService.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("New");
        verify(userDataRepository, times(1)).save(updated);
    }

    @Test
    void shouldThrowWhenUpdatingNonexistent() {
        when(userDataRepository.findById(99L)).thenReturn(Optional.empty());
        var ud = new UserData(99L, "X", null, null, "000", null);

        assertThatThrownBy(() -> userDataService.update(99L, ud))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("UserData not found");

        verify(userDataRepository, never()).save(any());
    }

    @Test
    void shouldDeleteUserDataById() {
        var ud = new UserData(1L, "To Delete", null, null, "999", null);
        when(userDataRepository.findById(1L)).thenReturn(Optional.of(ud));

        userDataService.deleteById(1L);

        verify(userDataRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonexistent() {
        when(userDataRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDataService.deleteById(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("UserData not found");

        verify(userDataRepository, never()).deleteById(any());
    }
}
