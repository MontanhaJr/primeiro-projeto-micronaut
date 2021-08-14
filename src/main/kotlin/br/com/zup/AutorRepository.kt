package br.com.zup

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.util.*

@Repository
interface AutorRepository : JpaRepository<Autor, Long>{
//    fun findByEmail(email: String, pageable: Pageable) : Page<Autor>

//    @Query("select u from Autor as u where u.email = :email")
    @Query(nativeQuery = true, value = "select * from Autor where email = :email")
    fun findByEmail(email: String, pageable: Pageable) : Optional<Autor>

}