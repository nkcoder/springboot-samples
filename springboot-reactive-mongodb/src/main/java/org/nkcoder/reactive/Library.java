package org.nkcoder.reactive;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Library {

  @Id
  private String id;

  private final List<Book> books;

  @JsonCreator
  public Library(List<Book> books) {
    this.books = books;
  }

}
