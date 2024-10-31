package com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    /** Un cliente puede tener varias facturas. Por eso es ManyToOne */
    /** El Many en este caso apunta a Invoice que es la clase donde esta */
    /** Esto se relacionara con el id del cliente de la clase Client */
    @ManyToOne
    @JoinColumn(name = "id_client_temp")
    private Client client;

    /** Constructor personalizado */
    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Invoice))
            return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
