package br.edu.cardapio.controller;

import br.edu.cardapio.model.Categoria;
import br.edu.cardapio.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaController {

    @Inject
    CategoriaService categoriaService;

    @GET
    public Response listarTodas() {
        List<Categoria> categorias = categoriaService.listarTodas();
        return Response.ok(categorias).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            return Response.ok(categoria).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Categoria categoria) {
        try {
            Categoria novaCatoria = categoriaService.criar(categoria);
            return Response.status(Response.Status.CREATED).entity(novaCatoria).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Categoria categoria) {
        try {
            Categoria categoriaAtualizada = categoriaService.atualizar(id, categoria);
            return Response.ok(categoriaAtualizada).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        try {
            categoriaService.remover(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    public static class ErrorMessage {
        public String message;

        public ErrorMessage(String message) {
            this.message = message;
        }
    }
}