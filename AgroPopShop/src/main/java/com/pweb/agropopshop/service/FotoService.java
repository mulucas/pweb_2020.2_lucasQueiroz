package com.pweb.agropopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.agropopshop.model.Foto;
import com.pweb.agropopshop.repository.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository imageGalleryRepository;
	
	public void salvarFoto(Foto imageGallery) {
		imageGalleryRepository.save(imageGallery);	
	}
	public void excluiFoto(Foto imageGallery) {
		imageGalleryRepository.delete(imageGallery);	
	}

	public List<Foto> getAllActiveImages() {
		return imageGalleryRepository.findAll();
	}

	public Optional<Foto> getFotoById(Long id) {
		return imageGalleryRepository.findById(id);
	}
}

