package br.com.senior.hotelaria.converter;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.senior.hotelaria.model.AccommodationEntity;
import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.service.AccomodationService;

/**
 * Conversor de {@link AccommodationEntity} estadias para {@link CustomerDTO} DTO.
 * 
 * @author João Heckmann
 *
 */
@Component
@Scope("prototype")
public class CustomerConverter {

	@Autowired
	private AccomodationService accomodationService;

	/**
	 * Converte uma lista de estadias para DTO.
	 * 
	 * @param accomodationList {@link List} Lista de {@link AccommodationEntity} estadias.
	 * @return Lista de {@link CustomerDTO} DTOs.
	 */
	public List<CustomerDTO> toRepresentation(List<AccommodationEntity> accomodationList) {
		return accomodationList.stream() //
				.map(this::toDTO) //
				.collect(toList());
	}

	/**
	 * Converte uma estadia para {@link CustomerDTO} DTO.
	 * 
	 * @param accomodation {@link AccommodationEntity} Estadia a ser convertida.
	 * @return {@link CustomerDTO} DTO criado.
	 */
	private CustomerDTO toDTO(AccommodationEntity accomodation) {
		CustomerDTO customerDTO = new CustomerDTO();
		CustomerEntity customer = accomodation.getCustomer();
		customerDTO.setName(customer.getName());
		customerDTO.setDocument(customer.getName());
		customerDTO.setPhone(customer.getPhone());
		customerDTO.setLastLodgingValue(accomodationService.findLastAccomodationValue(customer.getId()));
		customerDTO.setTotalRevenue(accomodationService.getTotalRevenue(customer.getId()));
		return customerDTO;
	}

}
