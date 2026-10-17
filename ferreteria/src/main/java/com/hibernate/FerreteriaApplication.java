package com.hibernate;

import java.nio.channels.Pipe.SourceChannel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hibernate.Repository.Repo_articulos;
import com.hibernate.entity.Articulos;

@SpringBootApplication
public class FerreteriaApplication implements CommandLineRunner  {

	@Autowired
	private Repo_articulos repositorios;

	public static void main(String[] args) {
		SpringApplication.run(FerreteriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Aplicación iniciada corectamente");
		List<Articulos>articulos = repositorios.findAll();
		articulos.stream().forEach(System.out::println);
		
	}

}
