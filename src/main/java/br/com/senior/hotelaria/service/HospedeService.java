package br.com.senior.hotelaria.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.senior.hotelaria.model.Hospede;
import br.com.senior.hotelaria.model.QHospede;
import br.com.senior.hotelaria.repository.HospedeRepository;

@Service
public class HospedeService {
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Atualiza um hóspede com dado outro objeto hospede
	 * @param id 
	 * @param hospede objeto com os novo dados
	 * @return Hospede alterado
	 * @throws EntityNotFoundException
	 */
	public Hospede atualizar(Long id, Hospede hospede) {
		Hospede hospedeBanco = findByIdThrowsException(id);
		if (hospedeBanco != null) {
			hospedeBanco.setTelefone(hospede.getTelefone());
			hospedeBanco.setNome(hospede.getNome());
			hospedeBanco.setDocumento(hospede.getDocumento());
			return hospedeRepository.save(hospedeBanco);
		}
		throw new EntityNotFoundException();
	}
	
	/**
	 * Busca um hóspede pelo seu ID.
	 * @param id
	 * @return Hospede
	 */
	private Hospede findByIdThrowsException(Long id) {
		Optional<Hospede> hospedeSalvo = hospedeRepository.findById(id);
		return hospedeSalvo.orElse(null);
	}
	
	/**s
	 * Busca hóspedes pelo seu nome, documento ou telefone.
	 * @param nome
	 * @param documento
	 * @param telefone
	 * @return Lista de hóspedes
	 */
	public List<Hospede> findHospedesByNameDocumentOrCellphone(Hospede hospede) {
		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(QHospede.hospede.nome.toUpperCase().like("%" + hospede.getNome().toUpperCase() + "%") //
				.or(QHospede.hospede.documento.toUpperCase().like(hospede.getDocumento().toUpperCase())) //
				.or(QHospede.hospede.telefone.toUpperCase().like("%" + hospede.getTelefone().toUpperCase() + "%")));
		
		JPAQuery<Hospede> query = new JPAQuery<>(em);
		query.from(QHospede.hospede).where(predicate);
		return query.fetch();
	}
	
	
}
