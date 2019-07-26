package org.nkcoder.reactive;

import lombok.Getter;

@Getter
public class Book {

  private final String id;

  private final String name;

  public Book(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
