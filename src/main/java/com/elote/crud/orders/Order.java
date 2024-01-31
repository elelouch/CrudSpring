package com.elote.crud.orders;

import com.elote.crud.client.Client;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CLIENT_ORDER")
public class Order {
    @Id @GeneratedValue private Long id;
    private String additionalInfo;
    private State state;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Order() {}

    public Order(String additionalInfo, State state, Client client) {
        this.additionalInfo = additionalInfo;
        this.state = state;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(additionalInfo, order.additionalInfo) && state == order.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, additionalInfo, state);
    }
}
