package br.com.softhouse.dende.modules.usuarios.dto;

import br.com.softhouse.dende.modules.usuarios.model.Usuario;

import java.time.LocalDate;
import java.time.Period;

public record UsuarioDTO(
        long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String idade
) {

}
