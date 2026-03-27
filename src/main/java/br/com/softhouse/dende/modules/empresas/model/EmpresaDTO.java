package br.com.softhouse.dende.modules.empresas.model;

import java.util.Date;

public record EmpresaDTO (
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        Date dataAbertura
){}
