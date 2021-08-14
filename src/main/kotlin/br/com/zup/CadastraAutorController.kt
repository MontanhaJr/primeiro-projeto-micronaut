package br.com.zup

import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {

    @Transactional
    @Post
    fun cadastra(@Body @Valid request: AutorRequest): HttpResponse<Any> {
//        request.run {
//            "${nome.toUpperCase()}, ${descricao.toUpperCase()}"
//        }.let(::println)

        val enderecoResponse = enderecoClient.consulta(request.cep)

        val autor = request.paraAutor(enderecoResponse.body()!!)
        autorRepository.save(autor)

        val uri = UriBuilder.of("/autores/{id}")
                            .expand(mutableMapOf(Pair("id", autor.id)))

        return HttpResponse.created(autor.toResponse(), uri)
    }

    @Transactional
    @Get
    fun listaAutores(@QueryValue(defaultValue = "") email: String) : HttpResponse<Any> {

        if (email.isBlank()) {
            val autores = autorRepository.findAll()
            return HttpResponse.ok(autores.map { autor -> autor.toResponse() })
        }

        val possivelEmail = autorRepository.findByEmail(email, Pageable.from(0, 2))

        if (possivelEmail.isEmpty) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(possivelEmail.map { autor -> autor.toResponse() })

    }

    @Transactional
    @Put("/{id}")
    fun atualizaDescricaoAutor(@PathVariable id: Long, descricao:String) : HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()

        autor.descricao = descricao

        return HttpResponse.ok(AutorResponse(autor))
    }

    @Transactional
    @Delete("/{id}")
    fun deletarAutor(@PathVariable id: Long) : HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        autorRepository.deleteById(id)

        return HttpResponse.ok()
    }
}