package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represent an order
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
public class OrderDto extends AbstractDto implements Serializable {

    private OrderStatus status;
    @NotEmpty(message = "Departure city should not be empty")
    private String fromCity;
    @NotEmpty(message = "Destination city should not be empty")
    private String toCity;
    private CargoDto cargo;
    private Integer distance;
    private Integer duration;
    private TruckDto truck;

    public OrderDto() {
        status = OrderStatus.NOT_COMPLETED;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (!Objects.equals(fromCity, orderDto.fromCity))
            return false;
        if (!Objects.equals(toCity, orderDto.toCity))
            return false;
        if (!Objects.equals(distance, orderDto.distance))
            return false;
        return Objects.equals(duration, orderDto.duration);
    }

    @Override
    public int hashCode() {
        int result = fromCity != null ? fromCity.hashCode() : 0;
        result = 31 * result + (toCity != null ? toCity.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + super.getId() +
                ", status=" + status +
                ", fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", cargo=" + cargo +
                ", distance=" + distance +
                ", truck=" + truck +
                '}';
    }
}
