package br.edu.cardapio.dto;

import br.edu.cardapio.model.Prato;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PratoComCategoriaDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Long categoriaId;
    private String categoriaNome;
    private String categoriaDescricao;
    private String urlImagem;
    private Boolean disponivel;
    private LocalDateTime criadoEm;

    public PratoComCategoriaDTO() {
    }

    public PratoComCategoriaDTO(Prato prato) {
        this.id = prato.getId();
        this.nome = prato.getNome();
        this.descricao = prato.getDescricao();
        this.preco = prato.getPreco();
        this.categoriaId = prato.getCategoriaId();
        this.urlImagem = prato.getUrlImagem();
        this.disponivel = prato.getDisponivel();
        this.criadoEm = prato.getCriadoEm();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getCategoriaDescricao() {
        return categoriaDescricao;
    }

    public void setCategoriaDescricao(String categoriaDescricao) {
        this.categoriaDescricao = categoriaDescricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}