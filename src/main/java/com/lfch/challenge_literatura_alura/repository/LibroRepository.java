package com.lfch.challenge_literatura_alura.repository;


import com.lfch.challenge_literatura_alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    Optional<Libro> findByTitulo(String titulo);
    @Query(value = "select * from libros",nativeQuery = true)
    List<Libro> busquedaSQLNativo();
}


