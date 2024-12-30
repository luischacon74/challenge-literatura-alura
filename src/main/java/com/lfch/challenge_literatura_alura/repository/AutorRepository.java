package com.lfch.challenge_literatura_alura.repository;


import com.lfch.challenge_literatura_alura.model.Autor;
import com.lfch.challenge_literatura_alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query("select a from Autor a \n" +
            "\twhere a.fechaNacimiento >= :fechaMinima\n" +
            "\tand  a.fechaNacimiento <= :fechaMaxima\n" +
            "\torder by a.fechaNacimiento ")
    List<Autor> busquedaJPQL(Integer fechaMaxima, Integer fechaMinima);

    @Query("SELECT COUNT(l) " +
            "FROM Autor a JOIN a.libro l " +
            "WHERE a.fechaNacimiento >= :fechaMinima " +
            "AND a.fechaNacimiento <= :fechaMaxima")
    int cuentaLibrosPorFechas(@Param("fechaMaxima") Integer fechaMaxima,
                              @Param("fechaMinima") Integer fechaMinima);


    @Query("select a from Autor a \n" +
            "\twhere a.fechaMuerte >= :fechaMinima\n" +
            "\tand  a.fechaMuerte <= :fechaMaxima\n" +
            "\torder by a.fechaMuerte desc")
    List<Autor> busquedaFechaMuerte(Integer fechaMaxima, Integer fechaMinima);
    Optional<Autor> findByNombre(String nombre);

    @Query("select a from Autor a \n" +
            "\twhere a.edad = :edadDigitada\n" +
            "\torder by a.edad desc")
    List<Autor> busquedaPorEdad(Integer edadDigitada);

    @Query("select a from Autor a \n" +
            "\twhere a.edad >= :edadMinima\n" +
            "\tand  a.edad <= :edadMaxima\n" +
            "\torder by a.edad desc")
    List<Autor> busquedaRangoEdad(Integer edadMaxima, Integer edadMinima);

}

