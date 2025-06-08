package com.boaglio.poc.book;

import net.jqwik.api.*;
import org.assertj.core.api.Assertions;

public class BookTest {

  static long counter=0;

  @Property
  void differentBooks(@ForAll("books") Book book) {
    // different books!
    System.out.println(++counter +" - "+book);

    // Validate title constraints: 10-100 characters
    Assertions.assertThat(book.getTitle())
            .as("Title should be between 10-100 characters")
            .hasSizeBetween(10, 100);

    // Validate author constraints: 5-21 characters
    Assertions.assertThat(book.getAuthor())
            .as("Author should be between 5-21 characters")
            .hasSizeBetween(5, 21);

    // Validate page count constraints: 0-450 pages
    Assertions.assertThat(book.getQtyOfPages())
            .as("Page count should be between 0-450")
            .isBetween(0, 450);

    // Additional validation: Check title contains only valid characters
    Assertions.assertThat(book.getTitle())
            .as("Title should contain only valid characters")
            .matches("[a-zA-Z0-9 \\-']+");

    // Additional validation: Check author contains only valid characters
    Assertions.assertThat(book.getAuthor())
            .as("Author should contain only valid characters")
            .matches("[a-zA-Z \\-]+");
  }
 
  @Provide
  Arbitrary<Book> books() {
    Arbitrary<String> titles = Arbitraries.strings().withCharRange(
       'a', 'z')
        .ofMinLength(10).ofMaxLength(100);
    Arbitrary<String> authors = Arbitraries.strings().withCharRange(
       'a', 'z')
        .ofMinLength(5).ofMaxLength(21);
    Arbitrary<Integer> qtyOfPages = Arbitraries.integers().between(
       0, 450);
 
 
    return Combinators.combine(titles, authors, qtyOfPages)
        .as((title, author, pages) -> new Book(title, author, pages));
  }
}