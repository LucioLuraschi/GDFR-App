package fr.isep.gdfrapi.model;

import jdash.common.entity.GDUserProfile;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

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
    @Column(name = "mod_status", nullable = true)
    private String modStatus;

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
    public String getModstatus() { return modStatus; }

    public void setGDStats(@NotNull GDUserProfile user) {
        this.idGD = (int) user.accountId();
        this.stars = user.stars();
        this.diamonds = user.diamonds();
        this.secretCoins = user.secretCoins();
        this.userCoins = user.userCoins();
        this.demons = user.demons();
        this.cps = user.creatorPoints();
        this.rank = user.globalRank();
        this.modStatus = user.role().get().name();
    }
}
