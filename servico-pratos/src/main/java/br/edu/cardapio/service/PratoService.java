package br.edu.cardapio.service;

import br.edu.cardapio.client.CategoriaClient;
import br.edu.cardapio.dto.PratoComCategoriaDTO;
import br.edu.cardapio.model.Prato;
import br.edu.cardapio.repository.PratoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PratoService {

    @Inject
    PratoRepository pratoRepository;

    @Inject
    @RestClient
    CategoriaClient categoriaClient;

    public List<PratoComCategoriaDTO> listarTodos() {
        List<Prato> pratos = pratoRepository.listarTodos();
        return pratos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PratoComCategoriaDTO buscarPorId(Long id) {
        Prato prato = pratoRepository.buscarPorId(id);
        if (prato == null) {
            throw new RuntimeException("Prato não encontrado");
        }
        return converterParaDTO(prato);
    }

    public List<PratoComCategoriaDTO> buscarPorCategoria(Long categoriaId) {
        List<Prato> pratos = pratoRepository.buscarPorCategoria(categoriaId);
        return pratos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<PratoComCategoriaDTO> buscarDisponiveis() {
        List<Prato> pratos = pratoRepository.buscarDisponiveis();
        return pratos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PratoComCategoriaDTO criar(Prato prato) {
        validarPrato(prato);
        validarCategoria(prato.getCategoriaId());
        Prato novoPrato = pratoRepository.salvar(prato);
        return converterParaDTO(novoPrato);
    }

    @Transactional
    public PratoComCategoriaDTO atualizar(Long id, Prato pratoAtualizado) {
        Prato prato = pratoRepository.buscarPorId(id);
        if (prato == null) {
            throw new RuntimeException("Prato não encontrado");
        }
        
        validarPrato(pratoAtualizado);
        validarCategoria(pratoAtualizado.getCategoriaId());
        
        prato.setNome(pratoAtualizado.getNome());
        prato.setDescricao(pratoAtualizado.getDescricao());
        prato.setPreco(pratoAtualizado.getPreco());
        prato.setCategoriaId(pratoAtualizado.getCategoriaId());
        prato.setUrlImagem(pratoAtualizado.getUrlImagem());
        prato.setDisponivel(pratoAtualizado.getDisponivel());
        
        return converterParaDTO(prato);
    }

    @Transactional
    public void remover(Long id) {
        if (pratoRepository.buscarPorId(id) == null) {
            throw new RuntimeException("Prato não encontrado");
        }
        pratoRepository.remover(id);
    }

    private void validarPrato(Prato prato) {
        if (prato.getNome() == null || prato.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do prato é obrigatório");
        }
        if (prato.getPreco() == null || prato.getPreco().doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (prato.getCategoriaId() == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
    }

    private void validarCategoria(Long categoriaId) {
        try {
            categoriaClient.buscarPorId(categoriaId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
    }

    private PratoComCategoriaDTO converterParaDTO(Prato prato) {
        PratoComCategoriaDTO dto = new PratoComCategoriaDTO(prato);
        
        try {
            CategoriaClient.CategoriaDTO categoria = categoriaClient.buscarPorId(prato.getCategoriaId());
            dto.setCategoriaNome(categoria.nome);
            dto.setCategoriaDescricao(categoria.descricao);
        } catch (Exception e) {
            dto.setCategoriaNome("Categoria não disponível");
        }
        
        return dto;
    }
}