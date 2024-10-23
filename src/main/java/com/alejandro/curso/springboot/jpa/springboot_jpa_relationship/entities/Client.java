package com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

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

    /** Relacion de uno a muchos (un cliente puede tener varias direcciones) */
    // Con esta relacion se crea una tabla intermedia entre cliente y dirección
    // Explicacion de CascadeType.ALL: En caso de borrar un cliente se borran sus direcciones y viceversa
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinColumn(name = "id_client") // Es la columna de la tabla intermedia, asi se maneja todo en direcciones.
    @JoinTable(name ="tbl_clientes_to_direcciones", joinColumns = @JoinColumn(name = "id_client"), 
    inverseJoinColumns = @JoinColumn(name = "id_address") ,
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_address"})) // Esto es para manejar la tabla intermedia a nuestro antojo. Crearla nosotoros
    public List<Address> addresses;

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Client() {
        addresses = new ArrayList<>();
    }

}
