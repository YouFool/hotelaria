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

import br.com.senior.hotelaria.converter.EstadiaDTO;
import br.com.senior.hotelaria.converter.HospedeConverter;
import br.com.senior.hotelaria.model.Estadia;
import br.com.senior.hotelaria.repository.EstadiaRepository;
import br.com.senior.hotelaria.service.EstadiaService;

@RestController
@RequestMapping("/estadia")
public class EstadiaResource {
	
	@Autowired
	private EstadiaService estadiaService;
	
	@Autowired
	private EstadiaRepository estadiaRepository;
	
	@Autowired
	private HospedeConverter hospedeConverter;
	
	@PostMapping("/checkin")
	public ResponseEntity<Estadia> realizarCheckIn(@RequestBody Estadia estadiaRequest) {
		Estadia estadiaCriada = estadiaService.realizarCheckIn(estadiaRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadiaCriada);
	}
	
	@PostMapping("/checkout")
	private ResponseEntity<Estadia> realizarCheckOut(@RequestBody Estadia estadiaRequest) {
		Estadia estadiaBanco = estadiaService.realizarCheckOut(estadiaRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadiaBanco);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estadia> verificarEstadia(@PathVariable Long id) {
		Optional<Estadia> estadia = estadiaRepository.findById(id);
		return estadia.isPresent() ? ResponseEntity.ok(estadia.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/inativos")
	public List<EstadiaDTO> buscarHospedesInativos(){
		return hospedeConverter.toRepresentation(estadiaService.findHospedesInativos());
	}
	
	@GetMapping("/ativos")
	public List<EstadiaDTO> buscarHospedesAtivos(){
		return hospedeConverter.toRepresentation(estadiaService.findHospedesAtivos());
	}

}
