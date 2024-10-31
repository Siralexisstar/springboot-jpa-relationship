package com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    // Colocamos left join tenga o no tenga facturas
    @Query("select c from Client c left join fetch c.addresses where c.id = ?1")
    Optional<Client> findOneWithAddresses(Long id);

    // Colocamos left join tenga o no tenga facturas
    @Query("select c from Client c left join fetch c.invoices where c.id = ?1")
    Optional<Client> findOneWithInvoice(Long id);

    // Esta consulta nos trae la informacion de la tabla intermedia entre clientes y facturas
    // Colocamos left join tenga o no tenga facturas 
    // Colocamos left join tenga o no tenga direcciones
    // Hay que iniciar las listas como un Set con esta consulta por que no se podran meter valores duplicados
    @Query("select c from Client c left join fetch c.invoices left join c.addresses where c.id= ?1")
    Optional<Client> findOne(Long id);
}
