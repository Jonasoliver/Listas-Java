package com.autobots.automanager.controles;


import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio repositorio;

    @GetMapping("/{id}")
    public Documento obterDocumento(@PathVariable long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping
    public List<Documento> obterDocumentos() {
        return repositorio.findAll();
    }
    @PostMapping
    public void cadastrarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
    }
    @PutMapping
    public void atualizarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
    }

    @DeleteMapping("{id}")
    public void excluirDocumento(@PathVariable long id) {
        repositorio.deleteById(id);
    }
}
