package com.listar.Listar.servicios;

import com.listar.Listar.entidades.Autor;
import com.listar.Listar.entidades.Editorial;
import com.listar.Listar.entidades.Libro;
import com.listar.Listar.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.listar.Listar.repositorios.AutorRepo;
import com.listar.Listar.repositorios.EditorialRepo;
import com.listar.Listar.repositorios.LibroRepo;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepo LibroRepo;
    @Autowired
    private EditorialRepo EditorialRepo;
    @Autowired
    private AutorRepo AutorRepo;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, Integer autorId, Integer editorialId) throws MiExcepcion {

        Autor autor = AutorRepo.findById(autorId).get();
        Editorial editorial = EditorialRepo.findById(editorialId).get();

        validar(isbn, titulo, ejemplares);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setAutor(autor);
        libro.setAlta(new Date());
        libro.setEditorial(editorial);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        LibroRepo.save(libro);
    }
    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, Integer autorId, Integer editorialId) throws MiExcepcion {

        Optional<Autor> respuestaAutor = AutorRepo.findById(autorId);
        Optional<Editorial> respuestaEditorial = EditorialRepo.findById(editorialId);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaEditorial.isPresent()){
            autor = respuestaAutor.get();
        }
        if (respuestaAutor.isPresent()){
            editorial = respuestaEditorial.get();
        }

        validar(isbn, titulo, ejemplares);

        Optional<Libro> respuestaLibro = LibroRepo.findById(isbn);
        if (respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            LibroRepo.save(libro);
        }
    }

    public void validar(Long isbn, String titulo, Integer ejemplares) throws MiExcepcion {

        if (isbn == null) {
            throw new MiExcepcion("El ISBN NO PUEDE SER NULO");
        }
        if (titulo == null) {
            throw new MiExcepcion("El titulo NO PUEDE SER NULO");
        }
        if (ejemplares == null) {
            throw new MiExcepcion("Los ejemplares NO PUEDEN SER NULOS");
        }
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = LibroRepo.findAll();
        return libros;
    }

    public Libro getOne(Long isbn) {
        return LibroRepo.getOne(isbn);
    }



}
