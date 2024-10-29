package com.alejandro.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.alejandro.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// manyToOneFindById();
		// oneToMany();
		// removeAddress();
		removeAddressFindById();
	}

	@Transactional
	public void removeAddressFindById() {

		Optional<Client> optionalClient = clientRepository.findById(1L);
		optionalClient.ifPresent(client -> {
			/** Asignamos las direcciones a los clientes */
			Address address1 = new Address("Passeig del Canal", 13);
			Address address2 = new Address("Carrer del comerç", 66);

			client.getAddresses().add(address1);
			client.getAddresses().add(address2);

			/** Guardamos el elemento cliente */
			clientRepository.save(client);

			/** Imprimimos el elemento cliente */
			System.out.println(client);

			// Recuperamos el cliente actualizado para eliminar la dirección
			Optional<Client> updatedClient = clientRepository.findOne(1L);
			updatedClient.ifPresent(clientDB -> {
				// Buscar la dirección que queremos eliminar
				Address addressToRemove = clientDB.getAddresses().stream()
						.filter(address -> "Passeig del Canal".equals(address.getStreet()) && address.getNumber() == 66)
						.findFirst()
						.orElse(null);

				if (addressToRemove != null) {
					clientDB.getAddresses().remove(addressToRemove);
					clientRepository.save(clientDB); // Esto debería eliminar la dirección de la base de datos
					System.out.println("Address removed successfully!");
				} else {
					System.out.println("Address not found.");
				}

				System.out.println(clientDB);
			});
		});
	}

	@Transactional
	public void oneToManyFindById() {

		Optional<Client> optionalClient = clientRepository.findById(1L);
		optionalClient.ifPresent(client -> {
			/** Asignamos las direcciones a los clientes */
			Address address1 = new Address("Passeig del Canal", 13);
			Address address2 = new Address("Carrer del comerç", 66);

			client.getAddresses().add(address1);
			client.getAddresses().add(address2);

			/** Guardamos el elemento cliente */
			clientRepository.save(client);

			/** Imprimimos el elemento cliente */
			System.out.println(client);
		});

	}

	@Transactional
	public void removeAddress() {

		/** Creamos el elemento cliente */
		Client client = new Client("Fernando", "Alonso");

		/** Asignamos las direcciones a los clientes */
		Address address1 = new Address("Passeig del Canal", 13);
		Address address2 = new Address("Carrer del comerç", 66);

		/** Guardamos las direcciones desde cliente */
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		/** Guardamos el elemento cliente */
		clientRepository.save(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(clientDB -> {
			clientDB.getAddresses().remove(address1);
			clientRepository.save(clientDB);
			System.out.println(clientDB);
		});
	}

	@Transactional
	public void oneToMany() {

		/** Creamos el elemento cliente */
		Client client = new Client("Fernando", "Alonso");

		/** Asignamos las direcciones a los clientes */
		Address address1 = new Address("Passeig del Canal", 13);
		Address address2 = new Address("Carrer del comerç", 66);

		/** Guardamos las direcciones desde cliente */
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		/** Guardamos el elemento cliente */
		clientRepository.save(client);
	}

	@Transactional
	public void manyToOne() {

		/** Creamos el elemento cliente */
		Client client = new Client("Fernando", "Alonso");
		clientRepository.save(client);

		/** Creamos el elemento invoice */
		Invoice invoice = new Invoice("Factura material de oficina", 2000L);

		/** Asignamos el cliente al elemento invoice */
		invoice.setClient(client);
		/** Guardamos el elemento invoice */
		Invoice invoiceDB = invoiceRepository.save(invoice);

		/** Imprimimos el elemento invoice */
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindById() {

		/** Creamos el elemento cliente */
		Optional<Client> optionalClient = clientRepository.findById(1L);

		/**
		 * Con esto lo que hacemos es asignar la el cliente 1 si existe a una
		 * determinada factura
		 */
		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();

			/** Creamos el elemento invoice */
			Invoice invoice = new Invoice("Factura material de cocina", 4000L);

			/** Asignamos el cliente al elemento invoice */
			invoice.setClient(client);

			/** Guardamos el elemento invoice */
			Invoice invoiceDB = invoiceRepository.save(invoice);

			/** Imprimimos el elemento invoice */
			System.out.println(invoiceDB);
		}
	}

}
