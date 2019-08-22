package org.nkcoder.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
  public Resources<Resource<Player>> getByTeamAndBornAtDesc(@RequestParam("team") String team) {
    Iterable<Player> playersIterable = playerRepository.findAll(Sort.by(Order.desc("bornAt")));
    List<Player> players = StreamSupport.stream(playersIterable.spliterator(), false)
        .filter(p -> p.getTeam().equals(team))
        .collect(Collectors.toList());

    Resources<Resource<Player>> playerResources = Resources.wrap(players);
    playerResources.add(
        linkTo(methodOn(PlayerController.class).getByTeamAndBornAtDesc(team)).withRel("by-team"));
    return playerResources;
  }


}
