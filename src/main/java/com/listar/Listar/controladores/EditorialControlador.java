package com.listar.Listar.controladores;



import com.listar.Listar.entidades.Editorial;
import com.listar.Listar.excepciones.MiExcepcion;
import com.listar.Listar.repositorios.EditorialRepo;
import com.listar.Listar.servicios.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private EditorialRepo repo;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorialRegistro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) throws RuntimeException {
        try {
            editorialService.crearEditorial(nombre);
        } catch (MiExcepcion e) {
            throw new RuntimeException(e);
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap model){
        List<Editorial> editoriales = editorialService.editorialLista();
        model.put("editorialLista", editoriales);
        return "editorialLista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap model){
        model.put("autor", editorialService.getOne(id));
        return "editorialModificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificado(@PathVariable Integer id, String nombre, ModelMap model){
        editorialService.modificar(nombre, id);
        return "redirect:/editorial/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Integer id, ModelMap model){
        repo.deleteById(id);
        return "redirect:/editorial/lista";
    }

}
