package br.com.senior.hotelaria.builder;

import java.time.LocalDateTime;

import br.com.senior.hotelaria.model.AccommodationEntity;
import br.com.senior.hotelaria.model.CustomerEntity;

/**
 * Builder para {@link AccomodationEntity} estadia.
 * 
 * @author João Heckmann
 *
 */
public class AccomodationEntityBuilder {

	/**
	 * Instância a ser construída.
	 */
	private AccommodationEntity entity;

	/**
	 * Construtor privado.
	 */
	private AccomodationEntityBuilder() {
	}

	/**
	 * Cria uma estadia com o seu Id e a data de check-in.
	 */
	public static AccomodationEntityBuilder oneAccomodation() {
		AccomodationEntityBuilder builder = new AccomodationEntityBuilder();
		AccommodationEntity entity = new AccommodationEntity();
		entity.setId(1L);
		entity.setCheckInDate(LocalDateTime.now());
		builder.entity = entity;
		return builder;
	}

	/**
	 * Designa um Id específico para o cliente.
	 * 
	 * @param id Id a ser setado.
	 */
	public AccomodationEntityBuilder withId(long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * Designa um {@link CustomerEntity} cliente para a estadia.
	 * 
	 * @param customer Cliente a ser setado.
	 */
	public AccomodationEntityBuilder withCustomer(CustomerEntity customer) {
		entity.setCustomer(customer);
		return this;
	}

	/**
	 * Seta se é necessário garagem na estadia.
	 * 
	 * @param isNeeded Necessário garagem ou não.
	 */
	public AccomodationEntityBuilder withGarageNeeded(boolean isNeeded) {
		entity.setGarageNeeded(isNeeded);
		return this;
	}

	/**
	 * Seta se a estadia está ativa.
	 * 
	 * @param active Estadia ativa ou não.
	 */
	public AccomodationEntityBuilder withActive(boolean active) {
		entity.setActive(active);
		return this;
	}

	/**
	 * Seta o valor total da estadia.
	 * 
	 * @param value Valor a ser setado.
	 */
	public AccomodationEntityBuilder withAccomodationValue(int value) {
		entity.setAccomodationValue(value);
		return this;
	}

	/**
	 * Seta uma data específica para o check-in.
	 * 
	 * @param date {@link LocalDateTime} Data a ser setada.
	 */
	public AccomodationEntityBuilder withCheckInDate(LocalDateTime date) {
		entity.setCheckInDate(date);
		return this;
	}

	/**
	 * Retorna a instância construída.
	 */
	public AccommodationEntity build() {
		return this.entity;
	}
}
