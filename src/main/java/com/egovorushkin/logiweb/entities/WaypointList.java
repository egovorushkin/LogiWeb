package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.WaypointListStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
@Table(name = "waypoint_list")
public class WaypointList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_city_id")
    private City toCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WaypointListStatus status;

    public WaypointList() {
    }

    public WaypointList(int id, City fromCity, City toCity, Cargo cargo,
                        WaypointListStatus status) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.cargo = cargo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public WaypointListStatus getStatus() {
        return status;
    }

    public void setStatus(WaypointListStatus status) {
        this.status = status;
    }

    private String getDescription() {
        return fromCity.getName() + " -> " + toCity.getName() + " (" + cargo.getName() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaypointList that = (WaypointList) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return fromCity +" -> " + toCity +", " + cargo + ", " + status;
    }
}
