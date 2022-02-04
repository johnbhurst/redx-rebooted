// Copyright 2022 John Hurst
// John Hurst
// 2022-02-03

package redx.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest
@ActiveProfiles(profiles = "test")
class DocumentRepositoryTest extends Specification {

    @Resource
    DocumentRepository repository

    def "DocumentRepository is injected"() {
        expect:
        repository
    }

    def "findById() returns all fields"() {
        when:
        def documentOpt = repository.findById("d01")

        then:
        !documentOpt.empty
        def document = documentOpt.get()
        document.id == "d01"
    }


    def "Repository findAll() returns all documents"() {
        when:
        def documents = repository.findAll().sort { it.id }

        then:
        documents*.id == ["d01", "d02", "d03", "d04", "d05", "d06", "d07", "d08", "d09", "d10", "d11", "d12"]
    }
}

