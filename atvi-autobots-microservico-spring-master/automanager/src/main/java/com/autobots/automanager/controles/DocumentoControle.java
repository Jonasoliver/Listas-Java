package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente/{clienteId}/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @PostMapping("/adicionar")
    public void adicionarDocumento(@PathVariable long clienteId, @RequestBody Documento documento) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            cliente.getDocumentos().add(documento);
            repositorio.save(cliente);
        }
    }

    @PutMapping("/atualizar/{documentoId}")
    public void atualizarDocumento(@PathVariable long clienteId, @PathVariable long documentoId, @RequestBody Documento documentoAtualizado) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            for (Documento documento : cliente.getDocumentos()) {
                if (documento.getId().equals(documentoId)) {
                    documento.setTipo(documentoAtualizado.getTipo());
                    documento.setNumero(documentoAtualizado.getNumero());
                    break;
                }
            }
            repositorio.save(cliente);
        }
    }

    @DeleteMapping("/deletar/{documentoId}")
    public void deletarDocumento(@PathVariable long clienteId, @PathVariable long documentoId) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            cliente.getDocumentos().removeIf(documento -> documento.getId().equals(documentoId));
            repositorio.save(cliente);
        }
    }
}
