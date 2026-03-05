package br.com.softhouse.dende.modules.usuarios.dto;

public record ReativarUsuarioRequestDTO(
        String email,
        String senha
) {
}