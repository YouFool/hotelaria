package br.com.senior.hotelaria.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.senior.hotelaria.model.AccommodationEntity;
import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.repository.accomodation.AccomodationRepository;
import br.com.senior.hotelaria.repository.customer.CustomerRepository;

/**
 * Serviço para o gerenciamento de estadias.
 * 
 * @author João Heckmann
 *
 */
@Service
public class AccomodationService {

	@Autowired
	private AccomodationRepository accomodationRepository;

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Realiza o Check-in conforme o DTO da requisição.
	 * 
	 * @param accomodationRequest Requisição.
	 * @return {@link AccommodationEntity} Estadia criada.
	 */
	@Transactional
	public AccommodationEntity doCheckIn(AccommodationEntity accomodationRequest) {
		AccommodationEntity accomodationCheckIn = new AccommodationEntity();
		Optional<CustomerEntity> customerFound = customerRepository.findById(accomodationRequest.getCustomer().getId());

		accomodationCheckIn.setGarageNeeded(accomodationRequest.isGarageNeeded());
		accomodationCheckIn.setCustomer(customerFound.orElse(null));
		accomodationCheckIn.setCheckInDate(LocalDateTime.now());
		accomodationCheckIn.setActive(true);
		accomodationCheckIn.setCheckOutDate(null);
		accomodationCheckIn.setAccomodationValue(0);
		return accomodationRepository.save(accomodationCheckIn);
	}

	/**
	 * Realiza o Check-out conforme o DTO da requisição.
	 * 
	 * @param accomodationRequest Requisição.
	 * @return {@link AccommodationEntity} Estadia finalizada.
	 */
	@Transactional
	public AccommodationEntity doCheckOut(AccommodationEntity accomodationRequest) {
		// Recupero o objeto Estadia
		Optional<AccommodationEntity> accomodationFound = accomodationRepository.findById(accomodationRequest.getId());

		// Caso a estadia exista, faz o checkout e calcula o valor da estadia
		if (accomodationFound.isPresent()) {
			AccommodationEntity accomodationCheckout = accomodationFound.get();
			// Insiro a data e a hora do checkOut
			if (accomodationCheckout.getCheckOutDate() != null) {
				throw new IllegalStateException("Estadia já finalizada");
			}

			accomodationCheckout.setCheckOutDate(LocalDateTime.now());

			// Seto a estadia como terminada
			accomodationCheckout.setActive(false);

			// Calculo o valor da estadia
			accomodationCheckout
					.setAccomodationValue(this.calculateTotalAccomodationValue(accomodationCheckout.getCheckInDate(), //
							accomodationCheckout.getCheckOutDate(), accomodationCheckout.isGarageNeeded()));

			return accomodationRepository.save(accomodationCheckout);
		}
		throw new EntityNotFoundException(
				"Estadia do hóspede '" + accomodationRequest.getCustomer().getName() + "' não encontrada!");
	}

	/**
	 * Calcula o valor total da estadia de um hóspede.
	 * 
	 * @param checkInDate    Data do check-in.
	 * @param checkOutDate   Data do check-out.
	 * @param isGarageNeeded Foi requisitado ou não garagem.
	 * @return Valor total da estadia.
	 */
	private double calculateTotalAccomodationValue(LocalDateTime checkInDate, LocalDateTime checkOutDate,
			boolean isGarageNeeded) {

		// Feito aqui para melhorar a leitura do código
		LocalDate checkIn = checkInDate.toLocalDate();
		LocalDate checkOut = checkOutDate.toLocalDate();

		Period accomodationPeriod = Period.between(checkIn, checkOut);

		if (accomodationPeriod.getDays() == 0) {
			accomodationPeriod = accomodationPeriod.plusDays(1);
		}

		double accomodationTotalValue = 0;

		// Para cada dia da semana faço a conta da estadia diária
		for (int i = 0; i < accomodationPeriod.getDays(); i++) {
			accomodationTotalValue += this.calculateDailyValueByGarageAndWeekday(isGarageNeeded, checkIn);
			checkIn = checkIn.plusDays(1);
		}

		// Caso a data de saída seja depois das 16:30, é cobrada mais uma diária
		if (checkOutDate.toLocalTime().isAfter(LocalTime.of(16, 30))) {
			accomodationTotalValue += this.calculateDailyValueByGarageAndWeekday(isGarageNeeded, checkIn);
		}

		return accomodationTotalValue;
	}

	/**
	 * Calcula o valor diário da estadia do hóspede de acordo com o dia da semana e
	 * se foi requisitado garagem.
	 * 
	 * @param isGarageNeeded Foi requisitado ou não garagem.
	 * @param localDate      Dia da semana da estadia.
	 * @return Valor da diária da estadia.
	 */
	private double calculateDailyValueByGarageAndWeekday(boolean isGarageNeeded, LocalDate localDate) {
		double dailyTotal = 0;
		switch (localDate.getDayOfWeek()) {
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
		case FRIDAY:
			if (isGarageNeeded) {
				dailyTotal += 15;
			}

			dailyTotal += 120;
			break;
		case SATURDAY:
		case SUNDAY:
			if (isGarageNeeded) {
				dailyTotal += 20;
			}
			dailyTotal += 150;
			break;
		}
		return dailyTotal;
	}

	/**
	 * Busca todas as estadias e retorna o valor total gasto pelo cliente.
	 * 
	 * @param customerId Id do cliente a ser encontrado.
	 * @return Valor total gasto em estadias pelo hóspede.
	 */
	public double getTotalRevenue(Long customerId) {
		List<AccommodationEntity> accomodations = accomodationRepository.findAllByCustomerId(customerId);
		return accomodations.stream().mapToDouble(AccommodationEntity::getAccomodationValue).sum();
	}

	/**
	 * Encontra a última acomodação do cliente e retorna o seu valor.
	 * 
	 * @param customerId Id do cliente.
	 * @return Valor da última estadia.
	 */
	public double findLastAccomodationValue(Long customerId) {
		return accomodationRepository.findTopByCustomerIdOrderByIdDesc(customerId).getAccomodationValue();
	}

	/**
	 * Retorna todos os clientes que não tem estadia ativa.
	 * 
	 * @return Lista de clientes inativos.
	 */
	public List<AccommodationEntity> findIncativeCustomers() {
		List<AccommodationEntity> allAcomodations = accomodationRepository.findAll();
		return allAcomodations.stream() //
				.filter(AccommodationEntity::isActive) //
				.collect(toList());
	}

	/**
	 * Retorna todos os clientes que tem estadia ativa.
	 * 
	 * @return Lista de clientes ativos.
	 */
	public List<AccommodationEntity> findActiveCustomers() {
		List<AccommodationEntity> allAcomodations = accomodationRepository.findAll();
		return allAcomodations.stream() //
				.filter(acc -> !acc.isActive()) //
				.collect(toList());
	}

}
