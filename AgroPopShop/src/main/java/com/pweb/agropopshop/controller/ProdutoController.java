package com.pweb.agropopshop.controller;


import java.util.Collections;
import java.util.List;
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

import com.pweb.agropopshop.model.Produto;
import com.pweb.agropopshop.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	private ModelAndView andView;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroProduto")
	public ModelAndView inicio() {
		andView = new ModelAndView("cadastro/cadastroProduto");
		andView.addObject("produtoObj", new Produto());
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarproduto")
	public ModelAndView salvar(Produto produto) {
		produtoRepository.save(produto);
		andView = new ModelAndView("adicionado/produtoAdicionado");
		andView.addObject("produtoObj", new Produto());
		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listaProdutos")
	public ModelAndView listaprodutos() {
		andView = new ModelAndView("lista/listaProdutos");
		Iterable<Produto> produtosIt = produtoRepository.listaPorNome();
		andView.addObject("produtos", produtosIt);
		return andView;
	}

	@GetMapping("/editarProduto/{idProduto}")
	public ModelAndView editar(@PathVariable("idProduto") Long idProduto) {
		andView = new ModelAndView("cadastro/cadastroProduto");
		Optional<Produto> produto = produtoRepository.findById(idProduto);

		andView.addObject("produtoObj", produto.get());
		return andView;

	}

	@GetMapping("/removerProduto/{idProduto}")
	public ModelAndView excluir(@PathVariable("idProduto") Long idproduto) {

		produtoRepository.deleteById(idproduto);

		andView.addObject("produtos", produtoRepository.findAll());
		andView.addObject("produtoObj", new Produto());
		return andView;

	}

	@PostMapping("**/pesquisarProduto")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		andView = new ModelAndView("lista/listaProdutos");
		andView.addObject("produtos", produtoRepository.buscarProdutoPorNome(nomePesquisa));
		return andView;
	}
}

