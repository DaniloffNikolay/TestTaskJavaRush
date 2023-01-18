package com.game.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 12)
    private String name;
    @Column(length = 30)
    private String title;
    @Enumerated(EnumType.STRING)
    private Race race;
    @Enumerated(EnumType.STRING)
    private Profession profession;
    private  Integer experience;
    private Integer level;
    private Integer untilNextLevel;
    private Date birthday;
    private Boolean banned;

    public Player() {}

    public Player(String name, String title, Race race, Profession profession, Integer experience, Integer level, Integer untilNextLevel, Date birthday, Boolean banned) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Integer experience, Integer level, Integer untilNextLevel, Date birthday, Boolean banned) {
        this.id=id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getExperience() {
        return experience;
    }
    public Date getBirthday() {
        return birthday;
    }
    public String getTitle() {
        return title;
    }
    public Race getRace() {
        return race;
    }
    public Profession getProfession() {
        return profession;
    }
    public Boolean getBanned() {
        return banned;
    }
    public Integer getLevel() {
        return level;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setRace(Race race) {
        this.race = race;
    }
    public void setProfession(Profession profession) {
        this.profession = profession;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
