package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente/{clienteId}/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @PostMapping("/adicionar")
    public void adicionarEndereco(@PathVariable long clienteId, @RequestBody Endereco endereco) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            cliente.setEndereco(endereco);
            repositorio.save(cliente);
        }
    }

    @PutMapping("/atualizar/{enderecoId}")
    public void atualizarEndereco(@PathVariable long clienteId, @PathVariable long enderecoId, @RequestBody Endereco enderecoAtualizado) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            Endereco endereco = cliente.getEndereco();
            if (endereco != null && endereco.getId().equals(enderecoId)) {
                endereco.setEstado(enderecoAtualizado.getEstado());
                endereco.setNumero(enderecoAtualizado.getNumero());
                endereco.setCidade(enderecoAtualizado.getCidade());
                endereco.setBairro(enderecoAtualizado.getBairro());
                endereco.setRua(enderecoAtualizado.getRua());
                endereco.setInformacoesAdicionais(enderecoAtualizado.getInformacoesAdicionais());
                endereco.setCodigoPostal(enderecoAtualizado.getCodigoPostal());
                repositorio.save(cliente);
            }
        }
    }

    @DeleteMapping("/deletar/{enderecoId}")
    public void deletarEndereco(@PathVariable long clienteId, @PathVariable long enderecoId) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente != null) {
            Endereco endereco = cliente.getEndereco();
            if (endereco != null && endereco.getId().equals(enderecoId)) {
                cliente.setEndereco(null);
                repositorio.save(cliente);
            }
        }
    }
}
