package com.autobots.automanager.controles;


import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;

    @GetMapping("/{id}")
    public Endereco obterEndereco(@PathVariable Long id) {
        return  repositorio.findById(id).orElse(null);
    }

    @GetMapping
    public List<Endereco> obterEnderecos(){
        return repositorio.findAll();
    }

    @PostMapping
    public void cadastraEndereco(@RequestBody Endereco endereco) {
        repositorio.save(endereco);
    }

    @DeleteMapping("/{id}")
    public void deletaEndereco(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
