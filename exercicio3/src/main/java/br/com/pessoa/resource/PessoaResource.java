package br.com.pessoa.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pessoa.model.Pessoa;
import br.com.pessoa.service.PessoaService;

@RestController
@Controller
@RequestMapping("/pessoas")
public class PessoaResource {
	
	private PessoaService service;

	public PessoaResource(PessoaService service) {
		super();
		this.service = service;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Pessoa> listarPessoas(){
		return service.buscarTodos();
		
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa tarefa) {
		 Pessoa pessoaSalva = service.salvarPessoa(tarefa);
		 return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Pessoa> atualizarTarefa(@Valid @RequestBody Pessoa pessoa, @PathVariable Integer id) {

		Optional<Pessoa> retorno = service.buscarPorId(id);

		if (!retorno.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Pessoa pessoaEdit = service.editarTarefa(id, pessoa);
		return ResponseEntity.ok(pessoaEdit);
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {

		Optional<Pessoa> retorno = service.buscarPorId(id);

		if (!retorno.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		service.deletarPessoa(id);
		return ResponseEntity.noContent().build();
	}
	

}
