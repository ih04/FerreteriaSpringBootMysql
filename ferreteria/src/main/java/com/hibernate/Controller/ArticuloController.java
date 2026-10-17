package com.hibernate.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibernate.DTO.ArticulosDTO;
import com.hibernate.Mapper.ArticuloMapper;
import com.hibernate.Repository.Repo_articulos;
import com.hibernate.Service.ArticuloService;
import com.hibernate.entity.Articulos;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("articulos")
public class ArticuloController {
    @Autowired
    // private Repo_articulos repositorio;
    //Ahora cambiamos esto para hacer la mmalada desde servicio :
    private ArticuloService servicio;

    // @GetMapping("/articulos")
    // public List<Articulos> Consulta(){
    //     return (List<Articulos>) repositorio.findAll();
    // }

    //nueva versión con clase dto
    //!La tacho aquí porque me la llevo a Service para que se haga desde allí
    // @GetMapping("/articulos")
    // public List<ArticulosDTO> Consulta(){
    //     return  repositorio.findAll().stream().map(ArticuloMapper::toDTO).collect(Collectors.toList());
    // } 

    //En su lugar ahora ponemos: 
    @GetMapping
    public List<ArticulosDTO> listar(){
        return servicio.serv_consulta();
    }

    @GetMapping("/{id}")
    public ArticulosDTO buscaId(@PathVariable Long id){
        return servicio.serv_buscaId(id);
    }

    @PostMapping
    public ArticulosDTO insertaArticulo(@RequestBody ArticulosDTO dto){
        return servicio.serv_inserta(dto);
    }

    @PutMapping("/{id}")
    public ArticulosDTO actualizArticulo(@PathVariable Long id, @RequestBody ArticulosDTO dto){
        return servicio.serv_actualiza(id,dto);
    }

    @DeleteMapping("/{id}")
    public String borraArticulo(@PathVariable Long id){
        return servicio.serv_borra(id);
    }
}
