package com.matheus.api.Controller;

import com.matheus.api.Model.Pessoa;
import com.matheus.api.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Operation(summary = "Listar todas as pessoas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem carregada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoa.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Não Encontrado")
    })
    @GetMapping
    public List<Pessoa> getPessoas() {
        return pessoaRepository.findAll();
    }

    @Operation(summary = "Buscar pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoa.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Não Encontrado")
    })
    @GetMapping("/{id}")
    public Pessoa getPessoa(@Parameter(description = "Pessoa a ser buscada pelo ID") @PathVariable Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    @Operation(summary = "Criar pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa criada com sucesso",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Pessoa.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content),
            @ApiResponse(responseCode = "404", description = "Não Encontrado")
    })
    @PostMapping
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Operation(summary = "Atualizar pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoa.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Não Encontrado")
    })
    @PutMapping("/{id}")
    public Pessoa atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(pessoaAtualizada.getNome());
                    pessoa.setIdade(pessoaAtualizada.getIdade());
                    return pessoaRepository.save(pessoa);
                })
                .orElse(null);
    }
}
