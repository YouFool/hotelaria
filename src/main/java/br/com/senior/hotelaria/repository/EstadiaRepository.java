package br.com.senior.hotelaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.hotelaria.model.Estadia;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {

}
