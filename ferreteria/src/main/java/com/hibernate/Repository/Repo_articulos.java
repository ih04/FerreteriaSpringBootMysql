package com.hibernate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.entity.Articulos;

@Repository
public interface Repo_articulos extends JpaRepository<Articulos, Long>{
    
}
