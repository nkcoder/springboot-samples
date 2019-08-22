package org.nkcoder.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/library")
@Profile("mvc")
@Slf4j
public class LibraryController {

  private final LibraryRepository libraryRepository;

  public LibraryController(LibraryRepository libraryRepository) {
    this.libraryRepository = libraryRepository;
  }

  @GetMapping("")
  public Flux<Library> allLibraries() {
    log.info("get all libraries");
    return libraryRepository.findAll();
  }


  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<Library> createLibrary(@RequestBody Mono<Library> library) {
    return libraryRepository.insert(library).next();
  }

  @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Flux<Library> batchCreateLibrary(@RequestBody Flux<Library> libraryFlux) {
    return libraryRepository.insert(libraryFlux);
  }

}
