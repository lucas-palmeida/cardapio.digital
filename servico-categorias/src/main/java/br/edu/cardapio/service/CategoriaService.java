package br.edu.cardapio.service;

import br.edu.cardapio.model.Categoria;
import br.edu.cardapio.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.listarTodas();
    }

    public Categoria buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.buscarPorId(id);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada");
        }
        return categoria;
    }

    @Transactional
    public Categoria criar(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        return categoriaRepository.salvar(categoria);
    }

    @Transactional
    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = buscarPorId(id);
        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setDescricao(categoriaAtualizada.getDescricao());
        return categoria;
    }

    @Transactional
    public void remover(Long id) {
        buscarPorId(id);
        categoriaRepository.remover(id);
    }
}