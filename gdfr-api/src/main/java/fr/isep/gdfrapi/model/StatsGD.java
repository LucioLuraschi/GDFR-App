package fr.isep.gdfrapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "statsgd")
public class StatsGD {
    @Id
    @Column(name = "id_gd", nullable = true)
    private Integer idGD;
    @Column(name = "stars", nullable = true)
    private Integer stars;
    @Column(name = "diamonds", nullable = true)
    private Integer diamonds;
    @Column(name = "secret_coins", nullable = true)
    private Integer secretCoins;
    @Column(name = "user_coins", nullable = true)
    private Integer userCoins;
    @Column(name = "demons", nullable = true)
    private Integer demons;
    @Column(name = "cps", nullable = true)
    private Integer cps;
    @Column(name = "rank", nullable = true)
    private Integer rank;

    public Integer getStars() {
        return stars;
    }
    public Integer getDiamonds() {
        return diamonds;
    }
    public Integer getSecretCoins() {
        return secretCoins;
    }
    public Integer getUserCoins() {
        return userCoins;
    }
    public Integer getDemons() {
        return demons;
    }
    public Integer getCps() {
        return cps;
    }
    public Integer getRank() {
        return rank;
    }
}
