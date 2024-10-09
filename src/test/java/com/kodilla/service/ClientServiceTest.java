package com.kodilla.service;

import com.kodilla.domain.Client;
import com.kodilla.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClients() {
        // Given
        Client client1 = new Client(1L, "John", "Doe", "john.doe@example.com", "654258647");
        Client client2 = new Client(2L, "Jane",  "Smith", "jane.smith@example.com", "46565874");
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        // When
        List<Client> clients = clientService.getAllClients();

        // Then
        assertEquals(2, clients.size());
        assertEquals("John", clients.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById_WhenClientExists() {
        // Given
        Client client = new Client(1L, "John",  "Doe", "john.doe@example.com", "654258647");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // When
        Client foundClient = clientService.getClientById(1L);

        // Then
        assertNotNull(foundClient);
        assertEquals("John", foundClient.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClientById_WhenClientDoesNotExist() {
        // Given
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.getClientById(1L);
        });

        assertEquals("Client not found with id: 1", exception.getMessage());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveClient() {
        // Given
        Client client = new Client(1L, "John",  "Doe", "john.doe@example.com", "654258647");
        when(clientRepository.save(client)).thenReturn(client);

        // When
        Client savedClient = clientService.saveClient(client);

        // Then
        assertNotNull(savedClient);
        assertEquals("John", savedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testUpdateClient() {
        // Given
        Client existingClient = new Client(1L, "John",  "Doe", "john.doe@example.com", "654258647");
        Client updatedDetails = new Client(2L, "Jane",  "Smith", "jane.smith@example.com", "46565874");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(existingClient);

        // When
        Client updatedClient = clientService.updateClient(1L, updatedDetails);

        // Then
        assertNotNull(updatedClient);
        assertEquals("Jane", updatedClient.getName());
        assertEquals("jane.smith@example.com", updatedClient.getEmail());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    void testDeleteClient_WhenClientExists() {
        // Given
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        // When
        clientService.deleteClient(1L);

        // Then
        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClient_WhenClientDoesNotExist() {
        // Given
        when(clientRepository.existsById(1L)).thenReturn(false);
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {clientService.deleteClient(1L);
        });

        assertEquals("Client not found with id: 1", exception.getMessage());
        verify(clientRepository, times(1)).existsById(1L);
    }

}