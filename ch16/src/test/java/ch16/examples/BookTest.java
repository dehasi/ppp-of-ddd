package ch16.examples;

import ch16.examples.Book.ISBN;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test void books_identity_is_its_isbn() {
        var isbnForThisBook = "9781118714706";
        var isbn = new ISBN(isbnForThisBook);

        var book = new Book(isbn);

        assertThat(isbn).isEqualTo(book.isbn);
        assertThat(isbnForThisBook).isEqualTo(book.id);
    }
}