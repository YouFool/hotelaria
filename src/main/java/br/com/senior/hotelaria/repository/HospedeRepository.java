package br.com.senior.hotelaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.hotelaria.model.Hospede;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {

}
