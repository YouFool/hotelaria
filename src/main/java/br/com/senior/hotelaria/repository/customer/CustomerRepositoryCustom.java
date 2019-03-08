package br.com.senior.hotelaria.repository.customer;

import java.util.List;

import br.com.senior.hotelaria.model.CustomerEntity;

/**
 * Interface para métodos que não usam Spring Data.
 * 
 * @author João Heckmann
 *
 */
public interface CustomerRepositoryCustom {

	/**
	 * Encontra {@link CustomerEntity} clientes por nome, documento ou telefone.
	 * 
	 * @param name     Nome.
	 * @param document Documento.
	 * @param phone    Telefone.
	 */
	public List<CustomerEntity> findCustomersByFields(String name, String document, String phone);

}
