package com.pweb.agropopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.agropopshop.model.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

}

