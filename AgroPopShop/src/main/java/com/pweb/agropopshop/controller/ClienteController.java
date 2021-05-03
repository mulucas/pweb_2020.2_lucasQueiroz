package com.pweb.agropopshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pweb.agropopshop.model.Cliente;
import com.pweb.agropopshop.repository.ClienteRepository;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	private ModelAndView andView;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroCliente")
	public ModelAndView inicio() {
		andView = new ModelAndView("cadastro/cadastroCliente");
		andView.addObject("clienteObj", new Cliente());
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarcliente")
	public ModelAndView salvar(Cliente cliente) {
		clienteRepository.save(cliente);
		andView = new ModelAndView("adicionado/clienteAdicionado");
		andView.addObject("clienteObj", new Cliente());
		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listaClientes")
	public ModelAndView listaclientes() {
		andView = new ModelAndView("lista/listaClientes");
		Iterable<Cliente> clientesIt = clienteRepository.findAll();
		andView.addObject("clientes", clientesIt);
		return andView;
	}

	@GetMapping("/editarCliente/{idCliente}")
	public ModelAndView editar(@PathVariable("idCliente") Long idCliente) {
		andView = new ModelAndView("cadastro/cadastroCliente");
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);

		andView.addObject("clienteObj", cliente.get());
		return andView;

	}

	@GetMapping("/removerCliente/{idCliente}")
	public ModelAndView excluir(@PathVariable("idCliente") Long idcliente) {

		clienteRepository.deleteById(idcliente);

		andView.addObject("clientes", clienteRepository.findAll());
		andView.addObject("clienteObj", new Cliente());
		return andView;

	}

	@PostMapping("**/pesquisarCliente")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		andView = new ModelAndView("lista/listaClientes");
		andView.addObject("clientes", clienteRepository.buscarClientePorNome(nomePesquisa));
		return andView;
	}
}
