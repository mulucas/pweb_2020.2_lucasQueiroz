package com.pweb.agropopshop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pweb.agropopshop.model.Foto;
import com.pweb.agropopshop.model.Produto;
import com.pweb.agropopshop.repository.ProdutoRepository;
import com.pweb.agropopshop.service.FotoService;

@Controller
public class FotoController {

	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private FotoService imageGalleryService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private ModelAndView andViewClienteDependente = new ModelAndView("cadastro/cadastroFoto");

	private Long idProdutoAtual;

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/foto/{idproduto}")
	public ModelAndView addProductPage(@PathVariable("idproduto") Long idproduto, Model map) {
		Optional<Produto> produto = produtoRepository.findById(idproduto);
		idProdutoAtual = idproduto;
		andViewClienteDependente.addObject("produtoobj", produto.get());
		show(map, idproduto);
		return andViewClienteDependente;
	}

	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct(Model model, HttpServletRequest request,
			final @RequestParam("image") MultipartFile file) {
		try {
			// String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName,
						HttpStatus.BAD_REQUEST);
			}
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			Foto foto = new Foto();
			Produto produto = produtoRepository.findById(idProdutoAtual).get();
			foto.setImage(imageData);
			foto.setProduto(produto);
			imageGalleryService.salvarFoto(foto);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
//			show(model, idProdutoAtual);
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Foto> imageGallery)
			throws ServletException, IOException {
		imageGallery = imageGalleryService.getFotoById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(imageGallery.get().getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/image/imageDetails")
	public ModelAndView showProductDetails(@RequestParam("id") Long id, Optional<Foto> imageGallery, Model model) {

		imageGallery = imageGalleryService.getFotoById(id);
		imageGalleryService.excluiFoto(imageGallery.get());
		
		return new ModelAndView("excluido");
	}

	@GetMapping("/image/show")
	String show(Model map, Long idproduto) {
		List<Foto> images = imageGalleryService.getAllActiveImages();
		if (!images.isEmpty()) {
			List<Foto> fotos = new ArrayList<Foto>();
			for (Foto foto : images) {
				if (foto.getProduto().getId() == idProdutoAtual) {
					fotos.add(foto);
				}
			}
			map.addAttribute("images", fotos);
		} else {
			map.addAttribute("images", images);
		}
		return "lista/listaFotos";
	}
}
