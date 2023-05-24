package com.listar.Listar.servicios;

import com.listar.Listar.entidades.Autor;
import com.listar.Listar.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.listar.Listar.repositorios.AutorRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepo AutorRepo;
    @Transactional
    public void crearAutor(String nombre) throws MiExcepcion {
        Autor autor = new Autor();
        if (nombre == null) {
            throw new MiExcepcion("No puede ser nulo el nombre");
        }
        autor.setNombre(nombre);
        AutorRepo.save(autor);
    }
    @Transactional
    public void modificarAutor(String nombre, Integer id){
        Optional<Autor> respuesta = AutorRepo.findById(id);
        if (respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            AutorRepo.save(autor);
        }
    }

    public Autor getOne(Integer id) {
        return AutorRepo.getOne(id);
    }

    public List<Autor> listarAutores(){
        return AutorRepo.findAll();
    }
}
