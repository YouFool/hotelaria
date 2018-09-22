package br.com.senior.hotelaria.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.senior.hotelaria.model.Estadia;
import br.com.senior.hotelaria.model.Hospede;
import br.com.senior.hotelaria.model.QEstadia;
import br.com.senior.hotelaria.repository.EstadiaRepository;
import br.com.senior.hotelaria.repository.HospedeRepository;

@Service
public class EstadiaService {

	@Autowired
	private EstadiaRepository estadiaRepository;

	@Autowired
	private HospedeRepository hospedeRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Estadia> findHospedesInativos(){
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(QEstadia.estadia.ativo.eq(false));
		JPAQuery<Estadia> query = new JPAQuery<>(em);
		query.from(QEstadia.estadia).where(predicate);
		return query.fetch(); 
	}
	
	public List<Estadia> findHospedesAtivos() {
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(QEstadia.estadia.ativo.eq(true));
		JPAQuery<Estadia> query = new JPAQuery<>(em);
		query.from(QEstadia.estadia).where(predicate);
		return query.fetch(); 
	}
	
	public Estadia realizarCheckIn(Estadia estadiaRequest) {
		// Crio um novo objeto Estadia e salvo no banco com a data de entrada
		Estadia estadiaCheckIn = new Estadia();
		Optional<Hospede> hospedeEncontrado = hospedeRepository.findById(estadiaRequest.getHospede().getId());

		estadiaCheckIn.setHospedeId(hospedeEncontrado.orElse(null));
		estadiaCheckIn.setDataEntrada(LocalDateTime.now());
		estadiaCheckIn.setAdicionalVeiculo(estadiaRequest.isAdicionalVeiculo());
		estadiaCheckIn.setAtivo(true);
		estadiaCheckIn.setDataSaida(null);
		estadiaCheckIn.setValorEstadia(0);
		return estadiaRepository.save(estadiaCheckIn);
	}

	@Transactional
	public Estadia realizarCheckOut(Estadia estadiaRequest) {
		// Recupero o objeto Estadia
		Optional<Estadia> estadiaBanco = estadiaRepository.findById(estadiaRequest.getId());

		// Caso a estadia exista, faz o checkout e calcula o valor da estadia
		if (estadiaBanco.isPresent()) {
			Estadia estadiaCheckout = estadiaBanco.get();
			// Insiro a data e a hora do checkOut
			if (estadiaCheckout.getDataSaida() != null) {
				throw new IllegalAccessError("Estadia já finalizada");
			}
			estadiaCheckout.setDataSaida(LocalDateTime.now());

			// Seto a estadia como terminada
			estadiaCheckout.setAtivo(false);

			// Calculo o valor da estadia
			estadiaCheckout.setValorEstadia(calculateValorTotalEstadia(estadiaCheckout.getDataEntrada(), //
					estadiaCheckout.getDataSaida(), estadiaCheckout.isAdicionalVeiculo()));

			return estadiaRepository.save(estadiaCheckout);
		}
		throw new EntityNotFoundException("Estadia do hóspede '" + estadiaRequest.getHospede().getNome() + "' não encontrada!");
	}

	/**
	 * Calcula o valor total da estadia.
	 * @param dateTimeEntrada
	 * @param dateTimeSaida
	 * @param isAdicionalVeiculo
	 * @return Valor da estadia.
	 */
	private double calculateValorTotalEstadia(LocalDateTime dateTimeEntrada, LocalDateTime dateTimeSaida,
			boolean isAdicionalVeiculo) {

		// Feito aqui para melhorar a leitura do código
		LocalDate dataEntrada = dateTimeEntrada.toLocalDate();
		LocalDate dataSaida = dateTimeSaida.toLocalDate();

		Period periodoDaEstadia = Period.between(dataEntrada, dataSaida);
		
		if (periodoDaEstadia.getDays() == 0) {
			periodoDaEstadia = periodoDaEstadia.plusDays(1);
		}

		double valorEstadia = 0;
		
		// Para cada dia da semana faço a conta da estadia diária
		for (int i = 0; i < periodoDaEstadia.getDays(); i++) {
			double valorDiaria = 0;
			
			valorDiaria = calculateEstadiaByDaysAndVeiculo(isAdicionalVeiculo, dataEntrada, valorDiaria);

			valorEstadia += valorDiaria;
			dataEntrada = dataEntrada.plusDays(1);
		}
		
		// Caso a data de saída seja depois das 16:30, é cobrada mais uma diária
		if (dateTimeSaida.toLocalTime().isAfter(LocalTime.of(16, 30))) {
			valorEstadia = calculateEstadiaByDaysAndVeiculo(isAdicionalVeiculo, dataEntrada, valorEstadia);
		}
		
		return valorEstadia;
	}

	/**
	 * Calcula a estadia do hospede de acordo com o dia da semana e o adicional de veiculo. 
	 * @param isAdicionalVeiculo
	 * @param dataEntrada
	 * @param valorDiaria
	 * @return
	 */
	private double calculateEstadiaByDaysAndVeiculo(boolean isAdicionalVeiculo, LocalDate dataEntrada, double valorDiaria) {
		// Caso seja dia de semana terá valor X, caso contrário Y
		switch (dataEntrada.getDayOfWeek()) {
			case MONDAY:
			case TUESDAY:
			case WEDNESDAY:
			case THURSDAY:
			case FRIDAY:
				if (isAdicionalVeiculo) {
					valorDiaria += 15;
				}
				
				valorDiaria += 120;
				break;

			case SATURDAY:
			case SUNDAY:
				if (isAdicionalVeiculo) {
					valorDiaria += 20;
				}
				valorDiaria += 150;
				break;
		}
		return valorDiaria;
	}

	public double findValorTotalEstadias(Long hospedeId) {
		BooleanBuilder predicate = new BooleanBuilder();
		
		predicate.and(QEstadia.estadia.hospede.id.eq(hospedeId));
		
		JPAQuery<Estadia> query = new JPAQuery<>(em);
		query.from(QEstadia.estadia).where(predicate);
		List<Estadia> estadiasDoHospede = query.fetch();
		
		return estadiasDoHospede.stream().mapToDouble(Estadia::getValorEstadia).sum();
	}

	public double findValorUltimaEstadia(Long hospedeId) {
		BooleanBuilder predicate = new BooleanBuilder();

		predicate.and(QEstadia.estadia.hospede.id.eq(hospedeId)); //

		JPAQuery<Estadia> query = new JPAQuery<>(em);
		query.from(QEstadia.estadia).where(predicate).orderBy(QEstadia.estadia.id.desc());
		Estadia ultimaEstadia = query.fetchFirst();

		return ultimaEstadia.getValorEstadia();
	}

}
