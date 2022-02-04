// Copyright 2022 John Hurst
// John Hurst
// 2022-02-03

package redx.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.annotation.Resource
import java.time.LocalDateTime

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
        document.fileName == "document01.xml"
        document.fileSize == 1001
        document.fileDate == LocalDateTime.parse("2022-01-01T10:00:01")
        document.messageFrom == "FROM01"
        document.fromDescription == "from01"
        document.messageTo == "TO01"
        document.toDescription == "to01"
        document.messageId == "message001"
        document.messageDate == LocalDateTime.parse("2022-02-01T11:00:01")
        document.transactionGroup == "BAR"
        document.priority == "Medium"
        document.securityContext == "NEMMCO01"
        document.market == "NEM"
    }


    def "Repository findAll() returns all documents"() {
        when:
        def documents = repository.findAll().sort { it.id }

        then:
        documents*.id == ["d01", "d02", "d03", "d04", "d05", "d06", "d07", "d08", "d09", "d10", "d11", "d12"]
    }
}

