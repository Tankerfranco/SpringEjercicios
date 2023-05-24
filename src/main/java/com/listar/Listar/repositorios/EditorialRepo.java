package com.listar.Listar.repositorios;

import com.listar.Listar.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepo extends JpaRepository<Editorial, Integer> {
}
