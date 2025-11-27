package br.edu.cardapio.repository;

import br.edu.cardapio.model.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    public List<Categoria> listarTodas() {
        return listAll();
    }

    public Categoria buscarPorId(Long id) {
        return findById(id);
    }

    public Categoria salvar(Categoria categoria) {
        persist(categoria);
        return categoria;
    }

    public void remover(Long id) {
        deleteById(id);
    }
}