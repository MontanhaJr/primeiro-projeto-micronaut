package br.com.zup

import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class Autor(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field:Size(max = 400) var descricao: String,
    val endereco: Endereco,
    @field:NotBlank val placa: String?
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    val criadoEm: LocalDateTime = LocalDateTime.now()

    fun toResponse() : AutorResponse {
        return AutorResponse(nome, email, descricao, endereco, placa)
    }
}
