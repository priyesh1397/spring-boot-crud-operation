package com.javatpoint.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.model.Books;
import com.javatpoint.service.BooksService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BooksService booksService;

    @InjectMocks
    private BooksControllerTest booksController;

    @Test
    @Order(1)
    void getAllBooks() throws Exception {
        // Create Books objects based on the JSON data
        Books book1 = new Books(2, "To Kill a Mockingbird", "*", 16);
        Books book2 = new Books(3, null, "George Orwell", 19);
        Books book3 = new Books(4, null, "F. Scott Fitzgerald", 21);
        Books book4 = new Books(5, "Bala Reddy", "Reddy", 256);
        Books book5 = new Books(1, "Mukesh", "Muk", 5);

        List<Books> booksList = Arrays.asList(book1, book2, book3, book4, book5);

        // Stubbing the behavior of the service layer method
        when(booksService.getAllBooks()).thenReturn(booksList);

        // Perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Convert the expected response to JSON
        String expectedResponse = new ObjectMapper().writeValueAsString(booksList);

        // Assert the response
        assertEquals(expectedResponse, result.getResponse().getContentAsString());

        // Verify that the controller calls the service method
        Books expectedResult = booksService.getAllBooks().get(0);
        assertEquals(booksList.get(0), expectedResult);
    }

    @Test
    @Order(2)
    void getBooks() throws Exception {
        // Create a Books object based on the JSON data
        Books book = new Books(1, "Mukesh", "Muk", 5);

        // Stubbing the behavior of the service layer method
        when(booksService.getBooksById(1)).thenReturn(book);

        // Perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Convert the expected response to JSON
        String expectedResponse = new ObjectMapper().writeValueAsString(book);

        // Assert the response
        assertEquals(expectedResponse, result.getResponse().getContentAsString());

        // Verify that the controller calls the service method
        Books expectedResult = booksService.getBooksById(1);
        assertEquals(book, expectedResult);
    }

    @Test
    @Order(3)
    void getTotalPrice() throws Exception {
        // Stubbing the behavior of the service layer method
        when(booksService.getSumOfPrices()).thenReturn(317);

        // Perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/book/totalPrice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert the response
        assertEquals("317", result.getResponse().getContentAsString());

        // Verify that the controller calls the service method
        int expectedResult = booksService.getSumOfPrices();
        assertEquals(317, expectedResult);
    }

    @Test
    @Order(4)
    void deleteBook() throws Exception {
        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1"))
                .andExpect(status().isOk());

        // Verify that the controller calls the service method

    }

    @Test
    @Order(5)
    void saveBook() throws Exception {
        // Create a Books object based on the JSON data
        Books book = new Books(1, "Mukesh", "Muk", 5);

        // Perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the controller calls the service method


        // Assert the response
        assertEquals("1", result.getResponse().getContentAsString());
    }

    @Test
    @Order(6)
    void update() throws Exception {
        // Create a Books object based on the JSON data
        Books book = new Books(1, "Mukesh", "Muk", 5);

        // Perform the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/books")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the controller calls the service method

        // Assert the response
        String expectedResponse = new ObjectMapper().writeValueAsString(book);
        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
