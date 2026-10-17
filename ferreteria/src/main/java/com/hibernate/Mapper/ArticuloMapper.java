package com.hibernate.Mapper;

import com.hibernate.DTO.ArticulosDTO;
import com.hibernate.entity.Articulos;

public class ArticuloMapper {
    public static ArticulosDTO toDTO(Articulos articulo){
        return  new ArticulosDTO(
            articulo.getId(), 
            articulo.getNombreArticulo(), 
            articulo.getPrecio(), 
            articulo.getExistencia()
        );
    }

    public static Articulos toEntity(ArticulosDTO dto){
            Articulos articulo = new Articulos();
            articulo.setId((Long) dto.getId());
            articulo.setNombreArticulo(dto.getNombreAticulo());
            articulo.setPrecio(dto.getPrecio());
            articulo.setExistencia(dto.getExistencia());
            return articulo;

    }
}
