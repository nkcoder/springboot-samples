package org.nkcoder.reactive;

import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class Library {

  @Id
  private final String id;
  private final List<Book> books;

  public Library(String id, List<Book> books) {
    this.id = id;
    this.books = books;
  }
}
