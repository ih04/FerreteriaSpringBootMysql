package com.hibernate.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibernate.DTO.ArticulosDTO;
import com.hibernate.Mapper.ArticuloMapper;
import com.hibernate.Repository.Repo_articulos;
import com.hibernate.entity.Articulos;

@Service
public class ArticuloService {
    @Autowired
    private Repo_articulos repo;

    public List<ArticulosDTO> serv_consulta(){
        return  repo.findAll().stream().map(ArticuloMapper::toDTO).collect(Collectors.toList());
    }

    public ArticulosDTO serv_buscaId(Long id){
        Articulos articuloporId = repo.findById(id).orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        return ArticuloMapper.toDTO(articuloporId);
    }

    public ArticulosDTO serv_inserta(ArticulosDTO dto){
        Articulos articulo = ArticuloMapper.toEntity(dto);
        Articulos insertado = repo.save(articulo);
        return ArticuloMapper.toDTO(insertado);
    }

    public ArticulosDTO serv_actualiza(Long id, ArticulosDTO dto){
        Optional<Articulos> existe = repo.findById(id);

        if (existe.isPresent()) {
            Articulos articulo = existe.get();
            
            articulo.setNombreArticulo(dto.getNombreAticulo());
            articulo.setPrecio(dto.getPrecio());
            articulo.setExistencia(dto.getExistencia());
            
            Articulos actualizado = repo.save(articulo);
            return ArticuloMapper.toDTO(actualizado);
        } else {
            throw new RuntimeException("Articulo no encontrado con id: "+id);
        }
    }

    public String serv_borra(Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return "Se ha borrado la entrada";
        }else{
            return "No se ha encontrado la entrada";
        }
        
    }
}
