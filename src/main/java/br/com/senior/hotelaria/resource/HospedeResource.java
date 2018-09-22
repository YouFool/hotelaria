package br.com.senior.hotelaria.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.hotelaria.model.Hospede;
import br.com.senior.hotelaria.repository.HospedeRepository;
import br.com.senior.hotelaria.service.HospedeService;

@RestController
@RequestMapping("/hospede")
public class HospedeResource {

	@Autowired
	private HospedeRepository hospedeRepository;

	@Autowired
	private HospedeService hospedeService;

	// Criar um hóspede [OK]
	@PostMapping
	private ResponseEntity<Hospede> salvar(@Valid @RequestBody Hospede hospede, HttpServletResponse response) {
		Hospede hospedeToSave = hospedeRepository.save(hospede);
		return ResponseEntity.status(HttpStatus.CREATED).body(hospedeToSave);
	}

	// Recuperar apenas um hóspede [OK]
	@GetMapping("/{id}")
	public ResponseEntity<Hospede> findHospedeById(@PathVariable Long id) {
		Optional<Hospede> hospede = hospedeRepository.findById(id);
		return hospede.isPresent() ? ResponseEntity.ok(hospede.get()) : ResponseEntity.notFound().build();
	}

	// Atualizar um hóspede [OK]
	@PutMapping("/{id}")
	private ResponseEntity<Hospede> atualizar(@PathVariable Long id, @Valid @RequestBody Hospede hospede) {
		Hospede hospedeAlterado = hospedeService.atualizar(id, hospede);
		return ResponseEntity.ok().body(hospedeAlterado);
	}

	// Deletar um hóspede [OK]
	@DeleteMapping("/{id}")
	private void removeHospede(@PathVariable Long id) {
		hospedeRepository.deleteById(id);
	}

	// Listar todos os hóspedes [OK]
	@GetMapping
	public List<Hospede> listarHospedes() {
		return hospedeRepository.findAll();
	}
	
	// Buscar hóspedes cadastrados por nome, documento ou telefone [OK]
	@PostMapping("/buscarHospedes")
	public List<Hospede> buscarHospedes(@RequestBody Hospede hospede){
		return hospedeService.findHospedesByNameDocumentOrCellphone(hospede);
	}
	
}
