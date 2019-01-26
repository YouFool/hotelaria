package br.com.senior.hotelaria.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.repository.customer.CustomerRepository;
import br.com.senior.hotelaria.repository.customer.CustomerRepositoryImpl;

/**
 * Serviço para o gerenciamento de {@link CustomerEntity} clientes.
 * 
 * @author João Heckmann
 *
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerRepositoryImpl customerRepositoryImpl;

	/**
	 * Busca um cliente pelo seu ID e tenta atualizar seus dados.
	 * 
	 * @param id       Id.
	 * @param customer Objeto com os novos dados.
	 * @return Cliente que foi atualizado.
	 * @throws EntityNotFoundException Caso não encontre o cliente.
	 */
	public CustomerEntity updateCustomer(Long id, CustomerEntity customer) {
		Optional<CustomerEntity> customerOpt = customerRepository.findById(id);
		if (customerOpt.isPresent()) {
			CustomerEntity customerFound = customerOpt.get();
			customerFound.setPhone(customer.getPhone());
			customerFound.setName(customer.getName());
			customerFound.setDocument(customer.getDocument());
			return customerRepository.save(customerFound);
		}
		throw new EntityNotFoundException(CustomerEntity.class.toString());
	}

	/**
	 * Busca hóspedes pelo seu nome, documento ou telefone.
	 * 
	 * @param name     Nome a ser encontrado.
	 * @param document Documento a ser encontrado.
	 * @param phone    Telefone a ser encontrado.
	 * @return {@link List} Lista de {@link CustomerEntity} hóspedes.
	 */
	public List<CustomerEntity> findCustomersByNameDocumentOrCellphone(String name, String document, String phone) {
		return customerRepositoryImpl.findCustomersByFields(name, document, phone);
	}

}
