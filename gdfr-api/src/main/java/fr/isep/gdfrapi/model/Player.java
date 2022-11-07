package fr.isep.gdfrapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "id_gd", nullable = true)
    private Integer idGD;

    @Column(name = "id_dl", nullable = true)
    private Integer idDL;

    @OneToOne
    @JoinColumn(name = "id_gd", insertable = false, updatable = false, nullable = true)
    private StatsGD statsGD;

    @OneToOne
    @JoinColumn(name = "id_dl", insertable = false, updatable = false, nullable = true)
    private StatsDL statsDL;


    public Integer getIdDL() {
        return idDL;
    }
    public Integer getIdGD() {
        return idGD;
    }
    public String getUsername() {
        return username;
    }

    public StatsDL getStatsDL() {
        return statsDL;
    }

    public StatsGD getStatsGD() {
        return statsGD;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setIdDL(Integer idDL) {
        this.idDL = idDL;
    }
    public void setIdGD(Integer idGD) {
        this.idGD = idGD;
    }
}
