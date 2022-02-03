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

    def "Repository findAll() returns all documents"() {
        when:
        def documents = repository.findAll()
        def (txn1, txn2) = documents[0].transactions

        then:
        documents*.id == ["d1"]
        txn1.id == "t1"
        txn2.id == "t2"
    }
}

