package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;

	@GetMapping("/cliente/{id}")
	public Cliente obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		return selecionador.selecionar(clientes, id);
	}

	@GetMapping("/clientes")
	public List<Cliente> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		return clientes;
	}

	@PostMapping("/cadastro")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluir")
	public void excluirCliente(@RequestBody Cliente exclusao) {
		Cliente cliente = repositorio.getById(exclusao.getId());
		repositorio.delete(cliente);
	}

	@PostMapping("/{id}/adicionarDocumento")
	public void adicionarDocumento(@PathVariable long id, @RequestBody Documento documento) {
		Cliente cliente = repositorio.findById(id).orElse(null);
		if (cliente != null) {
			cliente.getDocumentos().add(documento);
			repositorio.save(cliente);
		}
	}
	@PostMapping("/{id}/adicionarTelefone")
	public void adicionarTelefone(@PathVariable long id, @RequestBody Telefone telefone) {
		Cliente cliente = repositorio.findById(id).orElse(null);
		if (cliente != null) {
			cliente.getTelefones().add(telefone);
			repositorio.save(cliente);
		}
	}
	@PostMapping("/{id}/adicionarEndereco")
	public void adicionarEndereco(@PathVariable long id, @RequestBody Endereco endereco) {
		Cliente cliente = repositorio.findById(id).orElse(null);
		if (cliente != null) {
			cliente.setEndereco(endereco);
			repositorio.save(cliente);
		}
	}

	@PutMapping("/{clienteId}/atualizarDocumento/{documentoId}")
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
		} else {

			throw new RuntimeException("Cliente não encontrado");
		}
	}
	@PutMapping("/{clienteId}/atualizarTelefone/{TelefoneId}")
	public void atualizarTelefone(@PathVariable long clienteId, @PathVariable long TelefoneId, @RequestBody Telefone TelefoneAtualizado) {
		Cliente cliente = repositorio.findById(clienteId).orElse(null);
		if (cliente != null) {

			for (Telefone telefone : cliente.getTelefones()) {
				if (telefone.getId().equals(TelefoneId)) {

					telefone.setDdd(TelefoneAtualizado.getDdd());
					telefone.setNumero(TelefoneAtualizado.getNumero());
					break;
				}
			}

			repositorio.save(cliente);
		} else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}
	@PutMapping("/{clienteId}/atualizarEndereco/{enderecoId}")
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
			} else {
				throw new RuntimeException("Endereço não encontrado");
			}
		} else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}

	@DeleteMapping("/{clienteId}/deletarEndereco/{enderecoId}")
	public void deletarEndereco(@PathVariable long clienteId, @PathVariable long enderecoId) {
		Cliente cliente = repositorio.findById(clienteId).orElse(null);
		if (cliente != null) {
			Endereco endereco = cliente.getEndereco();
			if (endereco != null && endereco.getId().equals(enderecoId)) {
				cliente.setEndereco(null);
				repositorio.save(cliente);

			} else {
				throw new RuntimeException("Endereço não encontrado");
			}
		}else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}
	@DeleteMapping("/{clienteId}/deletarTelefone/{telefoneId}")
	public void deletarTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
		Cliente cliente = repositorio.findById(clienteId).orElse(null);
		if (cliente != null) {
			List<Telefone> telefones = cliente.getTelefones();
			Telefone telefoneParaRemover = telefones.stream()
					.filter(telefone -> telefone.getId().equals(telefoneId))
					.findFirst()
					.orElse(null);

			if (telefoneParaRemover != null) {
				telefones.remove(telefoneParaRemover);
				repositorio.save(cliente);
			} else {
				throw new RuntimeException("Telefone não encontrado");
			}
		} else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}
	@DeleteMapping("/{clienteId}/deletarDocumento/{documentoId}")
	public void deletarDocumento(@PathVariable long clienteId, @PathVariable long documentoId) {
		Cliente cliente = repositorio.findById(clienteId).orElse(null);
		if (cliente != null) {
			List<Documento> documentos = cliente.getDocumentos();
			Documento documentoParaRemover = documentos.stream()
					.filter(documento -> documento.getId().equals(documentoId))
					.findFirst()
					.orElse(null);

			if (documentoParaRemover != null) {
				documentos.remove(documentoParaRemover);
				repositorio.save(cliente);
			} else {
				throw new RuntimeException("Documento não encontrado");
			}
		} else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}

}
