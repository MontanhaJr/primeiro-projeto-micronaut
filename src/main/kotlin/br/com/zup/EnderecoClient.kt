package br.com.zup

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client


@Client("\${viaCep}")
interface EnderecoClient {

    @Get(value = "/{cep}/xml/", consumes = [MediaType.APPLICATION_XML])
    fun consulta(@QueryValue cep: String) : HttpResponse<EnderecoResponse>
}