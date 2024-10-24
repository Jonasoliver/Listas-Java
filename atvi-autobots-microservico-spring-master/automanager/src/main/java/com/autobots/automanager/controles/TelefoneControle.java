package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente/{clienteId}/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @PostMapping("/adicionar")
    public void adicionarTelefone(@PathVariable long clienteId, @RequestBody Telefone telefone) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            cliente.getTelefones().add(telefone);
            repositorio.save(cliente);
        }
    }

    @PutMapping("/atualizar/{telefoneId}")
    public void atualizarTelefone(@PathVariable long clienteId, @PathVariable long telefoneId, @RequestBody Telefone telefoneAtualizado) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            for (Telefone telefone : cliente.getTelefones()) {
                if (telefone.getId().equals(telefoneId)) {
                    telefone.setDdd(telefoneAtualizado.getDdd());
                    telefone.setNumero(telefoneAtualizado.getNumero());
                    break;
                }
            }
            repositorio.save(cliente);
        }
    }

    @DeleteMapping("/deletar/{telefoneId}")
    public void deletarTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            cliente.getTelefones().removeIf(telefone -> telefone.getId().equals(telefoneId));
            repositorio.save(cliente);
        }
    }
}
