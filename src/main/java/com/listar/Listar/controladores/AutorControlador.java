package com.listar.Listar.controladores;

import com.listar.Listar.entidades.Autor;
import com.listar.Listar.excepciones.MiExcepcion;
import com.listar.Listar.repositorios.AutorRepo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.listar.Listar.servicios.AutorService;

import java.util.List;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorRepo repo;

    @GetMapping("/registrar")
    public String registrar(){
        return "autorRegistro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) throws RuntimeException {
        try {
            autorService.crearAutor(nombre);
        } catch (MiExcepcion e) {
            throw new RuntimeException(e);
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap model){
        List<Autor> autores = autorService.listarAutores();
        model.put("autorLista", autores);
        return "autorLista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap model){
    model.put("autor", autorService.getOne(id));
    return "autorModificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificado(@PathVariable Integer id, String nombre, ModelMap model){
    autorService.modificarAutor(nombre, id);
    return "redirect:/autor/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Integer id, ModelMap model){
        repo.deleteById(id);
        return "redirect:/autor/lista";
    }
}
