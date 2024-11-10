package dev.muralis.sports.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import dev.muralis.sports.domain.PlayerRanking;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PlayersController.class);

    public PlayersController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public List<PlayerRanking> getPlayers() {
        logger.info("Preparing ranking for all players");
        String url = "http://localhost:9095/tennis/ranking";
        List<String> players = List.of("Jannik Sinner","Alex Zverev","Carlos Alcaraz","Daniil Medvedev","Novak Djikovic");
        return players.stream().map(player -> {
            int ranking = this.restTemplate.getForObject(url + "/" + player, int.class);
            return new PlayerRanking(player, ranking);
        }).collect(Collectors.toList());
    }
}