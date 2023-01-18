package com.game.controller;


import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(@Autowired PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Player> getPlayers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false) PlayerOrder order,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize
    ) {
        name = isNull(name) ? "" : name;
        title = isNull(title) ? "" : title;
        after = isNull(after) ? 0 : after;
        before = isNull(before) ? Long.MAX_VALUE : before;
        minExperience = isNull(minExperience) ? 0 : minExperience;
        maxExperience = isNull(maxExperience) ? Integer.MAX_VALUE : maxExperience;
        minLevel = isNull(minLevel) ? 0 : minLevel;
        maxLevel = isNull(maxLevel) ? Integer.MAX_VALUE : maxLevel;
        order = isNull(order) ? PlayerOrder.ID : order;
        pageNumber = isNull(pageNumber) ? 0 : pageNumber;
        pageSize = isNull(pageSize) ? 3 : pageSize;

        return playerService.getPlayers(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                order,
                pageNumber,
                pageSize
        );
    }
    @GetMapping("/count")
    public Integer getPlayersCount(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String title,
                                   @RequestParam(required = false) Race race,
                                   @RequestParam(required = false) Profession profession,
                                   @RequestParam(required = false) Long after,
                                   @RequestParam(required = false) Long before,
                                   @RequestParam(required = false) Boolean banned,
                                   @RequestParam(required = false) Integer minExperience,
                                   @RequestParam(required = false) Integer maxExperience,
                                   @RequestParam(required = false) Integer minLevel,
                                   @RequestParam(required = false) Integer maxLevel
    ) {
        name = isNull(name) ? "" : name;
        title = isNull(title) ? "" : title;
        after = isNull(after) ? 0 : after;
        before = isNull(before) ? Long.MAX_VALUE : before;
        minExperience = isNull(minExperience) ? 0 : minExperience;
        maxExperience = isNull(maxExperience) ? Integer.MAX_VALUE : maxExperience;
        minLevel = isNull(minLevel) ? 0 : minLevel;
        maxLevel = isNull(maxLevel) ? Integer.MAX_VALUE : maxLevel;

        return playerService.getPlayersCount(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel
        );
    }
    @GetMapping("/{ID}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("ID") long id) {
        if (id<=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Player player;
        try {
            player = playerService.getPlayerById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    @PostMapping("/{ID}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("ID") long id,
                                               @RequestBody PlayerInfo info) {
        if (isNull(info.banned) && isNull(info.birthday) && isNull(info.title) && isNull(info.name) && isNull(info.experience) && isNull(info.profession) && isNull(info.race)) {
            return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerById(id));
        }
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (nonNull(info.name) && (info.name.length() > 12 || info.name.isEmpty())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (nonNull(info.title) && info.title.length() > 30) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (nonNull(info.birthday) && info.birthday < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (nonNull(info.birthday)) {
            LocalDate localDate = new Date(info.birthday).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int year = localDate.getYear();
            if (year < 2000 || year > 3000) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        boolean banned = !isNull(info.banned) && info.banned;

        if(nonNull(info.experience) && (info.experience < 0 || info.experience > 10_000_000)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


        Player player = playerService.updatePlayer(id, info.name, info.title, info.race, info.profession, info.birthday, info.banned, info.experience);
        if (isNull(player)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(player);
        }
    }
    @DeleteMapping("/{ID}")
    public ResponseEntity delete(@PathVariable("ID") long id) {
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Player player = playerService.deletePlayer(id);
        if (isNull(player)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerInfo info) {
        if (StringUtils.isEmpty(info.name) || info.name.length() > 12) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (info.title.length() > 30) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(info.race)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(info.profession)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(info.birthday) || info.birthday < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LocalDate localDate = new Date(info.birthday).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        if (year < 2000 || year > 3000) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        boolean banned = !isNull(info.banned) && info.banned;
        if(isNull(info.experience) || info.experience < 0 || info.experience > 10_000_000) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


        Player player = playerService.createPlayer(info.name, info.title, info.race, info.profession, info.birthday, info.banned, info.experience);
        return ResponseEntity.status(HttpStatus.OK).body(player);
    }
}
