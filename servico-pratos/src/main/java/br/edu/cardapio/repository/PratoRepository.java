package br.edu.cardapio.repository;

import br.edu.cardapio.model.Prato;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PratoRepository implements PanacheRepository<Prato> {

    public List<Prato> listarTodos() {
        return listAll();
    }

    public Prato buscarPorId(Long id) {
        return findById(id);
    }

    public List<Prato> buscarPorCategoria(Long categoriaId) {
        return list("categoriaId", categoriaId);
    }

    public List<Prato> buscarDisponiveis() {
        return list("disponivel", true);
    }

    public Prato salvar(Prato prato) {
        persist(prato);
        return prato;
    }

    public void remover(Long id) {
        deleteById(id);
    }
}