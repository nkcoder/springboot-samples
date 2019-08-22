package org.nkcoder.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LibraryRepository extends ReactiveMongoRepository<Library, String> {

}
