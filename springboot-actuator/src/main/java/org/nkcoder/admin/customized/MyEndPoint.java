package org.nkcoder.admin.customized;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "books", enableByDefault = true)
public class MyEndPoint {

  private List<Book> books = new ArrayList<>();

  @ReadOperation
  public List<Book> books() {
    return books;
  }

  @WriteOperation
  public List<Book> addBook(Integer id, String name) {
    books.add(new Book(id, name));
    return books;
  }

  @DeleteOperation
  public List<Book> deleteBook(int index) {
    if (index >= 0 && index < books.size()) {
      books.remove(index);
    }
    return books;
  }

  @Getter
  static class Book {

    private final Integer id;
    private final String name;

    private LocalDateTime createdAt;

    Book(Integer id, String name) {
      this.id = id;
      this.name = name;
      this.createdAt = LocalDateTime.now();
    }
  }

}
