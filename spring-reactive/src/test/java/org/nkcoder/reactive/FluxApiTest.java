package org.nkcoder.reactive;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxApiTest {

  @Test
  public void shouldVerifyOk_whenCreateFlux() {
    Mono.just("hello");
    Mono.justOrEmpty(Optional.empty());
    Flux<String> just = Flux.just("hello", "world", "!");
    just.subscribe();
    verifyString(just);

    Flux<String> fromArray = Flux.fromArray(new String[]{"hello", "world", "!"});
    verifyString(fromArray);

    Flux<String> fromList = Flux.fromIterable(
        List.of("hello", "world", "!")
    );
    verifyString(fromList);

    Flux<String> fromStream = Flux.fromStream(
        Stream.of("hello", "world", "!")
    );
    verifyString(fromStream);

  }

  @Test
  public void shouldVerifyOk_whenGenerateData() {
    Flux<Integer> range = Flux.range(0, 4);
    verifyInteger(range);

    Flux<Long> take = Flux.interval(Duration.ofMillis(100)).take(5);
    StepVerifier.create(take)
        .expectNext(0L)
        .expectNext(1L)
        .expectNext(2L)
        .expectNext(3L)
        .expectNext(4L)
        .verifyComplete();
  }

  @Test
  public void shouldVerifyOk_whenCombine() {
    Flux<Integer> flux1 = Flux.just(0, 1);
    Flux<Integer> flux2 = Flux.just(2, 3);
    verifyInteger(flux1.mergeWith(flux2));

    Flux<Integer> flux3 = Flux.just(0, 2);
    Flux<Integer> flux4 = Flux.just(1, 3);
    StepVerifier.create(flux3.zipWith(flux4))
        .expectNextMatches(p -> p.getT1() == 0 && p.getT2() == 1)
        .expectNextMatches(p -> p.getT1() == 2 && p.getT2() == 3)
        .verifyComplete();

  }

  @Test
  public void shouldVerifyOk_whenMapData() {
    Flux<Integer> length = Flux.just("Kobe", "Durant", "Curry")
        .map(String::length);
    StepVerifier.create(length)
        .expectNext(4)
        .expectNext(6)
        .expectNext(5)
        .verifyComplete();
  }

  @Test
  public void shouldVerifyOk_whenFlatMapData() {
    List<Integer> result = List.of(4, 5, 6);
    Flux<Integer> length = Flux.just("Kobe", "Durant", "Curry")
        .flatMap(name -> Mono.just(name).map(String::length).subscribeOn(Schedulers.parallel()));
    StepVerifier.create(length)
        .expectNextMatches(result::contains)
        .expectNextMatches(result::contains)
        .expectNextMatches(result::contains)
        .verifyComplete();
  }

  private void verifyString(Flux<String> flux) {
    StepVerifier.create(flux)
        .expectNext("hello")
        .expectNext("world")
        .expectNext("!")
        .verifyComplete();
  }

  private void verifyInteger(Flux<Integer> flux) {
    StepVerifier.create(flux)
        .expectNext(0)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .verifyComplete();
  }

}
