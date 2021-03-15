package org.nkcoder.jpa.builder;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.nkcoder.jpa.configuration.SpringApplicationContext;
import org.nkcoder.jpa.entity.Player;
import org.nkcoder.jpa.repository.PlayerRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerBuilder {

    private final Player player = new Player();

    public static PlayerBuilder withDefault() {
        return new PlayerBuilder().withId(1).withName("name").withTeam("team1").withJoinAt(LocalDate.now());
    }

    public Player build() {
        return player;
    }

    public Player persist() {
        PlayerRepository repository = SpringApplicationContext.getBean(PlayerRepository.class);
        return repository.saveAndFlush(player);
    }

    public PlayerBuilder withId(Integer id) {
        player.setId(id);
        return this;
    }

    public PlayerBuilder withName(String name) {
        player.setName(name);
        return this;
    }

    public PlayerBuilder withTeam(String team) {
        player.setTeam(team);
        return this;
    }

    public PlayerBuilder withJoinAt(LocalDate joinAt) {
        player.setJoinAt(joinAt);
        return this;
    }
}