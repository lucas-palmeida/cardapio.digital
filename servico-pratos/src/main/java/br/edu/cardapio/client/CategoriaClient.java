package br.edu.cardapio.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/categorias")
@RegisterRestClient(configKey = "categoria-api")
public interface CategoriaClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    CategoriaDTO buscarPorId(@PathParam("id") Long id);

    // DTO para receber dados da API de Categorias
    class CategoriaDTO {
        public Long id;
        public String nome;
        public String descricao;
    }
}