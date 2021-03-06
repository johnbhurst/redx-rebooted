// Copyright 2022 John Hurst
// John Hurst
// 2022-02-03

package redx.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

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
        documents*.id == ["d01", "d02", "d03", "d04", "d05", "d06", "d07", "d08", "d09", "d10", "d11", "d12", "d13", "d14", "d15", "d16", "d17", "d18", "d19"]
    }

    @Unroll
    def "Repository findForFilter(#filter) returns documents #result"() {
        expect:
        repository.findForFilter(filter).sort { it.id }*.id == result

        where:
        filter                                                               || result
        "fileName = 'document01.xml'"                                         | ["d01"]
        "fileName <= 'document02.xml'"                                        | ["d01", "d02"]
        "fileSize = 1002"                                                     | ["d02"]
        "fileDate = 2022-01-01T10:00:03"                                      | ["d03"]
        "fileDate > 2022-01-01T10:00:03 AND fileDate < 2022-01-01T10:00:06"   | ["d04", "d05"]
        "fileDate >= 2022-01-01T10:00:03 AND fileDate <= 2022-01-01T10:00:06" | ["d03", "d04", "d05", "d06"]
        "messageFrom = 'FROM04'"                                              | ["d04"]
        "fromDescription = 'from05'"                                          | ["d05"]
        "messageTo = 'TO06'"                                                  | ["d06"]
        "toDescription = 'to07'"                                              | ["d07"]
        "messageId = 'message008'"                                            | ["d08"]
        "messageDate = 2022-02-01T11:00:09"                                   | ["d09"]
        "transactionGroup = 'HSMD'"                                           | ["d10"]
        "priority = 'Low'"                                                    | ["d11"]
        "market = 'VICGAS'"                                                   | ["d12"]
        "market != 'NEM'"                                                     | ["d09", "d10", "d12"]
        "txn.transactionDate = 2022-03-01T12:13:01"                           | ["d13"]
        "txn.transactionId = 'trans1401'"                                     | ["d14"]
        "txn.initiatingTransactionId = 'init1501'"                            | ["d15"]
        "txn.transactionName = 'CATSChangeRequest'"                           | ["d16"]
        "term1701a"                                                           | ["d17"]
        "term1701b"                                                           | ["d17"]
        "term1801a"                                                           | ["d18"]
        "common1718"                                                          | ["d17", "d18"]
        "common1719"                                                          | ["d17", "d19"]
        "common1819"                                                          | ["d18", "d19"]
        "common1718 AND common1819"                                           | ["d18"]
        "common1718 AND NOT term1701a"                                        | ["d18"]
        "term1701a OR term1801a"                                              | ["d17", "d18"]
        "(common1718 OR common1819)"                                          | ["d17", "d18", "d19"]
        "(common1718 OR common1819) AND NOT term1801a"                        | ["d17", "d19"]
    }
}

