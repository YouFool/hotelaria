package br.com.senior.hotelaria.builder;

import br.com.senior.hotelaria.model.CustomerEntity;

/**
 * Builder para {@link CustomerEntity} cliente.
 * 
 * @author João Heckmann
 *
 */
public class CustomerEntityBuilder {

	/**
	 * Instância a ser construída.
	 */
	private CustomerEntity entity;

	/**
	 * Construtor privado.
	 */
	private CustomerEntityBuilder() {
	}

	/**
	 * Cria um cliente com apenas o seu Id.
	 */
	public static CustomerEntityBuilder oneCustomer() {
		CustomerEntityBuilder builder = new CustomerEntityBuilder();
		CustomerEntity entity = new CustomerEntity();
		entity.setId(1L);
		builder.entity = entity;
		return builder;
	}

	/**
	 * Designa um Id específico para o cliente.
	 * 
	 * @param id Id a ser setado.
	 */
	public CustomerEntityBuilder withId(long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * Designa um número do documento para o cliente.
	 * 
	 * @param document Documento a ser setado.
	 */
	public CustomerEntityBuilder withDocument(String document) {
		entity.setDocument(document);
		return this;
	}

	/**
	 * Designa um nome específico para o cliente.
	 * 
	 * @param name Nome a ser setado.
	 */
	public CustomerEntityBuilder withName(String name) {
		entity.setName(name);
		return this;
	}

	/**
	 * Designa um telefone específico para o cliente.
	 * 
	 * @param phone Telefone a ser setado.
	 */
	public CustomerEntityBuilder withPhone(String phone) {
		entity.setPhone(phone);
		return this;
	}

	/**
	 * Retorna a instância construída.
	 */
	public CustomerEntity build() {
		return this.entity;
	}
}
