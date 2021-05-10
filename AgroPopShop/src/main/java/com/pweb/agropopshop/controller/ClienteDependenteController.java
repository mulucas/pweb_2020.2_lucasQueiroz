package com.pweb.agropopshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pweb.agropopshop.model.Cliente;
import com.pweb.agropopshop.model.ClienteDependente;
import com.pweb.agropopshop.repository.ClienteDependenteRepository;
import com.pweb.agropopshop.repository.ClienteRepository;

@Controller
public class ClienteDependenteController {

	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Autowired
	private ClienteDependenteRepository clienteDependenteRepository;
	
	private ModelAndView andViewClienteDependente = new ModelAndView("cadastro/cadastroClienteDependente");
	
	@GetMapping("/removerclientedependente/{idClientesDependente}")
	public ModelAndView excluir(@PathVariable("idClientesDependente") Long idClientesDependente) {

		Cliente cliente = clienteDependenteRepository.findById(idClientesDependente).get().getCliente();
		clienteDependenteRepository.deleteById(idClientesDependente);

		andViewClienteDependente.addObject("clienteobj", cliente);
		andViewClienteDependente.addObject("clientesdependentes", clienteDependenteRepository.getClienteDependentes(cliente.getId()));
		return andViewClienteDependente;

	}

	
	@GetMapping("/clientesDependentes/{idcliente}")
	public ModelAndView clientesDependentes(@PathVariable("idcliente") Long idcliente) {

		Optional<Cliente> cliente = clienteRepository.findById(idcliente);
		
		andViewClienteDependente.addObject("clienteobj", cliente.get());
		andViewClienteDependente.addObject("clientesdependentes", clienteDependenteRepository.getClienteDependentes(idcliente));
		return andViewClienteDependente;
	}
	
	@PostMapping("**/addclientedependente/{clienteid}")
	public ModelAndView addDependenteCliente(ClienteDependente clienteDependente, @PathVariable("clienteid") Long clienteid) {
		
		Cliente cliente = clienteRepository.findById(clienteid).get();
		clienteDependente.setCliente(cliente);
		clienteDependenteRepository.save(clienteDependente);
		
		andViewClienteDependente.addObject("clienteobj", cliente);
		andViewClienteDependente.addObject("clientesdependentes", clienteDependenteRepository.getClienteDependentes(clienteid));
		
		return andViewClienteDependente;
	}

}
