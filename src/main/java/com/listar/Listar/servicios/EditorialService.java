package com.listar.Listar.servicios;

import com.listar.Listar.entidades.Autor;
import com.listar.Listar.entidades.Editorial;
import com.listar.Listar.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.listar.Listar.repositorios.EditorialRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepo EditorialRepo;
    @Transactional
    public void crearEditorial(String nombre) throws MiExcepcion {
        Editorial editorial = new Editorial();
        if (nombre == null) {
            throw new MiExcepcion("No puede ser nulo el nombre");
        }
        editorial.setNombre(nombre);
        EditorialRepo.save(editorial);
    }
    @Transactional
    public void modificar(String nombre, Integer id){
        Optional<Editorial> respuesta = EditorialRepo.findById(id);
        if (respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            EditorialRepo.save(editorial);
        }
    }

    public List<Editorial> editorialLista(){
        List<Editorial> editoriales = EditorialRepo.findAll();
        return editoriales;
    }

    public Editorial getOne(Integer id) {
        return EditorialRepo.getOne(id);
    }
}
