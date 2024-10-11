package com.kodilla.controller;

import com.kodilla.domain.Client;
import com.kodilla.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = new Client();
        client1.setId(1L);
        client1.setName("John");
        client1.setSurname("Doe");
        client1.setEmail("john.doe@example.com");
        client1.setPhoneNumber("654258647");

        client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane");
        client2.setSurname("Smith");
        client2.setEmail("jane.smith@example.com");
        client2.setPhoneNumber("46565874");
    }

    @Test
    void testGetAllClients() throws Exception {
        // Given
        List<Client> clients = Arrays.asList(client1, client2);
        Mockito.when(clientService.getAllClients()).thenReturn(clients);

        // When & Then
        mockMvc.perform(get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clients.size()))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }

    @Test
    void testGetClientById_Success() throws Exception {
        // Given
        Mockito.when(clientService.getClientById(1L)).thenReturn(client1);

        // When & Then
        mockMvc.perform(get("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    void testGetClientById_NotFound() throws Exception {
        // Given
        Mockito.when(clientService.getClientById(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/clients/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateClient() throws Exception {
        // Given
        Mockito.when(clientService.saveClient(any(Client.class))).thenReturn(client1);

        // When & Then
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"surname\":\"Doe\", \"email\":\"john.doe@example.com\", \"phoneNumber\":\"654258647\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateClient_Success() throws Exception {
        // Given
        Mockito.when(clientService.updateClient(anyLong(), any(Client.class))).thenReturn(client1);

        // When & Then
        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"John\", \"surname\":\"Doe\", \"email\":\"john.doe@example.com\", \"phoneNumber\":\"654258647\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    void testUpdateClient_NotFound() throws Exception {
        // Given
        Mockito.when(clientService.updateClient(anyLong(), any(Client.class)))
                .thenThrow(new RuntimeException("Client not found"));

        // When & Then
        mockMvc.perform(put("/api/clients/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"surname\":\"Doe\", \"email\":\"john.doe@example.com\", \"phoneNumber\":\"654258647\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteClient_Success() throws Exception {
        // Given
        Mockito.doNothing().when(clientService).deleteClient(1L);

        // When & Then
        mockMvc.perform(delete("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteClient_NotFound() throws Exception {
        // Given
        Mockito.doThrow(new RuntimeException("Client not found")).when(clientService).deleteClient(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/clients/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}