package com.autobots.automanager.controles;


import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;

    @GetMapping("/{id}")
    public Telefone obterTelefone(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping
    public List<Telefone> obterTelefones() {
        return repositorio.findAll();
    }

    @PostMapping
    public void adicionarTelefone(@RequestBody Telefone telefone) {
        repositorio.save(telefone);
    }

    @PutMapping
    public void atualizarTelefone(@RequestBody Telefone telefone) {
        repositorio.save(telefone);

    }

    @DeleteMapping("/{id}")
    public void removerTelefone(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
