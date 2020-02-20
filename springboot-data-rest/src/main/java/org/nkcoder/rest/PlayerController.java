package org.nkcoder.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@ResponseBody
public class PlayerController {

  private final PlayerRepository playerRepository;

  public PlayerController(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @GetMapping("/players/by-team")
  public CollectionModel<EntityModel<Player>> getByTeamAndJoinAtDesc(
      @RequestParam("teamId") Integer teamId) {
    Iterable<Player> playersIterable = playerRepository.findAll(Sort.by(Order.desc("joinAt")));
    List<Player> players = StreamSupport.stream(playersIterable.spliterator(), false)
        .filter(p -> p.getTeamId().equals(teamId))
        .collect(Collectors.toList());

    CollectionModel<EntityModel<Player>> playerResources = CollectionModel.wrap(players);
    playerResources.add(
        linkTo(methodOn(PlayerController.class).getByTeamAndJoinAtDesc(teamId)).withRel("by-team"));
    return playerResources;
  }

}
