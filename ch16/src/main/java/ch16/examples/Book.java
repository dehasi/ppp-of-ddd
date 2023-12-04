package ch16.examples;

class Book {

    String id;
    ISBN isbn;

    Book(ISBN isbn) {
        this.isbn = isbn;
        this.id = isbn.number;
    }

    // Simplified Value Object - see chapter 16 for robust implementations
    public static class ISBN {

        public String number;

        public ISBN(String isbn) {
            validate(isbn);

            this.number = isbn;
        }

        private static void validate(String isbn) {
            if (isbn.length() != 13 && isbn.length() != 10)
                throw new RuntimeException("ISBN must be 10  or 13 digits long");

            for (char c : isbn.toCharArray()) {
                if (c < '0' || c > '9')
                    throw new RuntimeException("ISBNs must contain only digits");
            }
        }
    }
}
