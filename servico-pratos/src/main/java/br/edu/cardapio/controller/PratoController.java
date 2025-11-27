package br.edu.cardapio.controller;

import br.edu.cardapio.dto.PratoComCategoriaDTO;
import br.edu.cardapio.model.Prato;
import br.edu.cardapio.service.PratoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoController {

    @Inject
    PratoService pratoService;

    @GET
    public Response listarTodos(@QueryParam("disponivel") Boolean disponivel,
                                 @QueryParam("categoriaId") Long categoriaId) {
        try {
            List<PratoComCategoriaDTO> pratos;
            
            if (categoriaId != null) {
                pratos = pratoService.buscarPorCategoria(categoriaId);
            } else if (disponivel != null && disponivel) {
                pratos = pratoService.buscarDisponiveis();
            } else {
                pratos = pratoService.listarTodos();
            }
            
            return Response.ok(pratos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Erro ao buscar pratos: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            PratoComCategoriaDTO prato = pratoService.buscarPorId(id);
            return Response.ok(prato).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Prato prato) {
        try {
            PratoComCategoriaDTO novoPrato = pratoService.criar(prato);
            return Response.status(Response.Status.CREATED).entity(novoPrato).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Erro ao criar prato: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Prato prato) {
        try {
            PratoComCategoriaDTO pratoAtualizado = pratoService.atualizar(id, prato);
            return Response.ok(pratoAtualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
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
            pratoService.remover(id);
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