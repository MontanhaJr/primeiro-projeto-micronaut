package br.com.zup

import io.micronaut.core.annotation.Introspected

@Introspected
data class AutorResponse(
    var nome: String,
    var email: String,
    var descricao: String,
    var endereco: Endereco?,
    var placa: String?
) {
    constructor(autor: Autor) : this(autor.nome, autor.email, autor.descricao, autor.endereco, autor.placa)

}
