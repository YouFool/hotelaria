package br.com.senior.hotelaria.handler;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.repository.customer.CustomerRepository;
import br.com.senior.hotelaria.service.CustomerService;

/**
 * Handler para clientes.
 * 
 * @author João Heckmann
 *
 */
@RestController
@RequestMapping("/hospede")
public class CustomerHandler {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;

	/**
	 * Cria um cliente com dada requisição.
	 * 
	 * @param toCreate {@link CustomerEntity} Cliente a ser criado.
	 * @return HTTP Status 201 com cliente criado.
	 */
	@PostMapping
	private ResponseEntity<CustomerEntity> createCustomer(@Valid @RequestBody CustomerEntity toCreate) {
		CustomerEntity hospedeToSave = customerRepository.save(toCreate);
		return ResponseEntity.status(HttpStatus.CREATED).body(hospedeToSave);
	}

	/**
	 * Recupera um cliente pelo seu ID.
	 * 
	 * @param id Id do cliente a ser encontrado.
	 * @return HTTP Status 200 caso seja encontrado ou 404 caso não exista.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CustomerEntity> retrieveCustomer(@PathVariable Long id) {
		Optional<CustomerEntity> hospede = customerRepository.findById(id);
		return hospede.isPresent() ? ResponseEntity.ok(hospede.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * Atualiza os dados de um cliente já existente.
	 * 
	 * @param id       Id do cliente a ser atualizado.
	 * @param toUpdate Cliente a ser atualizado.
	 * @return HTTP Status 200 e Cliente com dados atualizados.
	 */
	@PutMapping("/{id}")
	private ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id,
			@Valid @RequestBody CustomerEntity toUpdate) {
		CustomerEntity updatedCustomer = customerService.updateCustomer(id, toUpdate);
		return ResponseEntity.ok().body(updatedCustomer);
	}

	/**
	 * Deleta um cliente pelo seu id.
	 * 
	 * @param id Id do cliente a ser deletado.
	 */
	@DeleteMapping("/{id}")
	private void deleteCustomer(@PathVariable Long id) {
		customerRepository.deleteById(id);
	}

	/**
	 * Lista todos os clientes cadastrados.
	 * 
	 * @return {@link List} Lista com todos {@link CustomerEntity} clientes.
	 */
	@GetMapping
	public List<CustomerEntity> listAll() {
		return customerRepository.findAll();
	}

	/**
	 * Busca hóspedes por nome, documento ou telefone.
	 * 
	 * @param name     Nome a ser encontrado.
	 * @param document Documento a ser encontrado.
	 * @param phone    Telefone a ser encontrado.
	 * @return {@link List} Lista com {@link CustomerEntity} clientes que satisfazem
	 *         o critério.
	 */
	@PostMapping("/buscarHospedes")
	public List<CustomerEntity> buscarHospedes(@RequestParam String name, @RequestParam String document,
			@RequestParam String phone) {
		return customerService.findCustomersByNameDocumentOrCellphone(name, document, phone);
	}

}
