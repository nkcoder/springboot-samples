package org.nkcoder.reactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfig {

  private final LibraryRepository libraryRepository;

  public RouterFunctionConfig(LibraryRepository libraryRepository) {
    this.libraryRepository = libraryRepository;
  }

  @Bean
  public RouterFunction<?> routerFunction() {
    return route(GET("/library"), this::allLibraries)
        .andRoute(POST("/library"), this::createLibrary);
  }


  public Mono<ServerResponse> allLibraries(ServerRequest request) {
    return ServerResponse.ok().body(libraryRepository.findAll(), Library.class);
  }

  public Mono<ServerResponse> createLibrary(ServerRequest request) {
    Mono<Library> library = request.bodyToMono(Library.class);
    Mono<Library> savedLibrary = libraryRepository.insert(library).next();
    return ServerResponse.created(URI.create("http://localhost:8080/library/id"))
        .body(savedLibrary, Library.class);
  }

}
