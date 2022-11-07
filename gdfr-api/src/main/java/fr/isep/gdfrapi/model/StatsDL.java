package fr.isep.gdfrapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "statsdl")
public class StatsDL {

    @Id
    @Column(name = "id_dl", nullable = true)
    private Integer idDL;

    @Column(name = "hardest", nullable = true)
    private String hardest;

    @Column(name = "points", nullable = true)
    private Integer points;

    public Integer getPoints() {
        return points;
    }

    public String getHardest() {
        return hardest;
    }

}
