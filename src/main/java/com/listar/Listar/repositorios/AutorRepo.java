package com.listar.Listar.repositorios;

import com.listar.Listar.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepo extends JpaRepository<Autor, Integer> {
}
