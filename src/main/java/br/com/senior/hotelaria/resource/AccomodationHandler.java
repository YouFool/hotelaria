package br.com.senior.hotelaria.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.hotelaria.converter.CustomerConverter;
import br.com.senior.hotelaria.converter.CustomerDTO;
import br.com.senior.hotelaria.model.AccommodationEntity;
import br.com.senior.hotelaria.repository.accomodation.AccomodationRepository;
import br.com.senior.hotelaria.service.AccomodationService;

/**
 * Handler para estadias.
 * 
 * @author João Heckmann
 *
 */
@RestController
@RequestMapping("/estadia")
public class AccomodationHandler {

	@Autowired
	private AccomodationService accomodationService;

	@Autowired
	private AccomodationRepository accomodationRepository;

	@Autowired
	private CustomerConverter customerConverter;

	/**
	 * Realiza o check-in de um cliente.
	 * 
	 * @param accomodationRequest Request.
	 * @return Resposta HTTP 201 com o check-in realizado.
	 */
	@PostMapping("/checkin")
	public ResponseEntity<AccommodationEntity> checkIn(@RequestBody AccommodationEntity accomodationRequest) {
		final AccommodationEntity createdAccomodation = accomodationService.doCheckIn(accomodationRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccomodation);
	}

	/**
	 * Realiza o check-out de um cliente.
	 * 
	 * @param accomodationRequest Request.
	 * @return Resposta HTTP 200 com o check-out realizado.
	 */
	@PostMapping("/checkout")
	private ResponseEntity<AccommodationEntity> checkOut(@RequestBody AccommodationEntity accomodationRequest) {
		final AccommodationEntity accomodationFinished = accomodationService.doCheckOut(accomodationRequest);
		return ResponseEntity.status(HttpStatus.OK).body(accomodationFinished);
	}

	/**
	 * Encontra uma acomodação pelo seu identificador único.
	 * 
	 * @param id Id.
	 * @return Resposta HTTP 200 com a estadia encontrada.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AccommodationEntity> verifyAccomodation(@PathVariable Long id) {
		Optional<AccommodationEntity> accomodationFound = accomodationRepository.findById(id);
		return accomodationFound.isPresent() ? ResponseEntity.ok(accomodationFound.get())
				: ResponseEntity.notFound().build();
	}

	/**
	 * Retorna todos os hóspedes inativos.
	 * 
	 * @return Lista de {@link CustomerDTO} hóspedes que não estão ativos.
	 */
	@GetMapping("/inativos")
	public List<CustomerDTO> findInactiveCustomers() {
		return customerConverter.toRepresentation(accomodationService.findIncativeCustomers());
	}

	/**
	 * Retorna todos os hóspedes ativos.
	 * 
	 * @return Lista de {@link CustomerDTO} hóspedes que estão ativos.
	 */
	@GetMapping("/ativos")
	public List<CustomerDTO> findActiveCustomers() {
		return customerConverter.toRepresentation(accomodationService.findActiveCustomers());
	}

}
