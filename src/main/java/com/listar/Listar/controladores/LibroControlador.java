package com.listar.Listar.controladores;

import com.listar.Listar.entidades.Autor;
import com.listar.Listar.entidades.Editorial;
import com.listar.Listar.entidades.Libro;
import com.listar.Listar.excepciones.MiExcepcion;
import com.listar.Listar.repositorios.LibroRepo;
import com.listar.Listar.servicios.AutorService;
import com.listar.Listar.servicios.EditorialService;
import com.listar.Listar.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroRepo repo;

    @GetMapping("/lista")
    public String lista(ModelMap model){
        List<Libro> libros = libroService.listarLibros();
        model.put("libroLista", libros);
        return "libroLista.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model){
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.editorialLista();

        model.addAttribute("autores",autores);
        model.addAttribute("editoriales", editoriales);
        return "libroRegistro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer ejemplares, @RequestParam Integer idAutor, @RequestParam Integer idEditorial, ModelMap model){
        try {
            libroService.crearLibro(isbn,
                    titulo,
                    ejemplares,
                    idAutor,
                    idEditorial);
        } catch (MiExcepcion e) {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.editorialLista();

            model.addAttribute("autores",autores);
            model.addAttribute("editoriales", editoriales);
            model.put("error", e.getMessage());
            return "libroRegistro.html";
        }
        return "index.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap model){
        model.put("libro", libroService.getOne(isbn));
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.editorialLista();

        model.addAttribute("autores",autores);
        model.addAttribute("editoriales", editoriales);

        return "libroModificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, @RequestParam String titulo, @RequestParam Integer ejemplares, @RequestParam Integer idAutor, @RequestParam Integer idEditorial, ModelMap model){
        try {
            libroService.modificarLibro(isbn,titulo,ejemplares,idAutor,idEditorial);
        } catch (MiExcepcion e) {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.editorialLista();

            model.addAttribute("autores",autores);
            model.addAttribute("editoriales", editoriales);
            model.put("error", e.getMessage());
            return "libroModificar.html";

        }
        return "redirect:/libro/lista";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long isbn){
        repo.deleteById(isbn);
        return "redirect:/libro/lista";
    }

}
