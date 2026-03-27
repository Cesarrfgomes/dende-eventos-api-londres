package br.com.softhouse.dende.modules.usuarios.mappers;

import java.time.LocalDate;
import java.time.Period;

import br.com.softhouse.dende.modules.usuarios.dto.UsuarioDTO;
import br.com.softhouse.dende.modules.usuarios.model.Usuario;

public class UsuarioMapper {


    public UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getDataNascimento(),
            this.calcularIdade(usuario.getDataNascimento())
        );

    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        return usuario;
    }


    private String calcularIdade(LocalDate nascimento) {
        if (nascimento == null) {
            return null;
        }

        Period periodo = Period.between(nascimento, LocalDate.now());

        return periodo.getYears() + " anos, "
                + periodo.getMonths() + " meses e "
                + periodo.getDays() + " dias";
    }
}
