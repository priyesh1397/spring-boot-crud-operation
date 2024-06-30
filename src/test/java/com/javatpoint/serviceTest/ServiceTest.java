package com.javatpoint.serviceTest;

import com.javatpoint.model.Books;
import com.javatpoint.repository.BooksRepository;
import com.javatpoint.service.BooksService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;

    @Test
    void getAllBooks() {
        Books book1 = new Books(1, "Book 1", "Author 1", 100);
        Books book2 = new Books(2, "Book 2", "Author 2", 200);
        List<Books> booksList = new ArrayList<>();
        booksList.add(book1);
        booksList.add(book2);

        when(booksRepository.findAll()).thenReturn(booksList);

        List<Books> retrievedBooks = booksService.getAllBooks();

        assertEquals(2, retrievedBooks.size());
        assertEquals("Book 1", retrievedBooks.get(0).getBookname());
        assertEquals("Book 2", retrievedBooks.get(1).getBookname());
    }

}

