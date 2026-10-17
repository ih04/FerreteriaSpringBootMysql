package com.hibernate.DTO;

public class ArticulosDTO {
    //esta clase se usa para transferir datos entre capas
    private Long id;
    private String nombrearticulo;
    private Double precio;
    private Integer existencia;
    
    public ArticulosDTO(Long id, String nombrearticulo, Double precio, Integer existencia) {
        this.id = id;
        this.nombrearticulo = nombrearticulo;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAticulo() {
        return nombrearticulo;
    }

    public void setNombreAticulo(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        return "ArticulosDTO [id=" + id + ", nombrearticulo=" + nombrearticulo + ", precio=" + precio + ", cantidad="
                + existencia + "]"; 
    }

    
}
