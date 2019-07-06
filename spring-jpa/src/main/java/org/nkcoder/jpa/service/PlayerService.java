package org.nkcoder.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.nkcoder.jpa.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerService {

  private final PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public void printResults() {


  }

}
