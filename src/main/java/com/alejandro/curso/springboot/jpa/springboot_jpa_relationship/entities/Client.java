package com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

@Data
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @ToString.Exclude
    /** Relacion de uno a muchos (un cliente puede tener varias direcciones) */
    // Con esta relacion se crea una tabla intermedia entre cliente y dirección
    // Explicacion de CascadeType.ALL: En caso de borrar un cliente se borran sus
    // direcciones y viceversa
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_client") // Es la columna de la tabla intermedia, asi
    // se maneja todo en direcciones.
    @JoinTable(name = "tbl_clientes_to_direcciones", joinColumns = @JoinColumn(name = "id_client"), inverseJoinColumns = @JoinColumn(name = "id_address"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "id_address" })) // Esto es para manejar la tabla intermedia a nuestro antojo. Crearla nosotoros
    public Set<Address> addresses;

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;

    public Client() {
        addresses = new HashSet<>();
        invoices = new HashSet<>(); // Iniciamos el array --> tabla intermedia
    }

    public Client addInvoice(Invoice invoice) {
        invoices.add(invoice); // Agregamos la factura que pasamos por parámetro
        invoice.setClient(this); // Creamos la relacion inversa en un solo metodo
        return this;
    }

       @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
