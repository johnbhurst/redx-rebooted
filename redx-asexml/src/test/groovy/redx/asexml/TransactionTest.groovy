package redx.asexml

import groovy.xml.DOMBuilder
import spock.lang.Specification

import java.time.LocalDateTime

class TransactionTest extends Specification {

    private Document getDocument(String path) {
        new Document(DOMBuilder.newInstance(false, true).parseText(this.getClass().getResourceAsStream("/asexml/$path").text))
    }

    def "CATSNotification transaction properties are read"() {
        when:
        def document = getDocument("nemmsats_samples_r39_p1/NEMMSATS_CATSNotification_r39_p1.xml")

        then:
        document.from == "VENCORP"
        document.fromDescription == "Vencorp"
        document.to == "TXUN"
        document.toDescription == "TXU Networks"
        document.messageID == "AAA"
        document.messageDate == LocalDateTime.of(2002, 1, 1, 12, 0, 0)
        document.transactionGroup == TransactionGroup.CATS
        document.priority == Priority.Medium
        document.securityContext == null
        document.market == "VICGAS"

        def transactions = document.transactions
        transactions.size() == 2
        def txn1 = transactions[0]
        txn1.transactionID == "XXX"
        txn1.transactionDate == LocalDateTime.of(2002,1 ,1, 12, 0, 0)
        txn1.initiatingTransactionID == null
        txn1.transactionName == "CATSNotification"
        txn1.text("Role") == "CDB"
        txn1.text("ChangeRequest/RequestID") == "123456789012345"

        def txn2 = transactions[1]
        txn2.transactionID == "YYY"
        txn2.transactionDate == LocalDateTime.of(2002, 1, 2, 12, 0, 0)
        txn2.initiatingTransactionID == null
        txn2.transactionName == "CATSNotification"
        txn2.text("Role") == "CDB"
        txn2.text("ChangeRequest/RequestID") == "123456789012346"
    }

    def "ReportResponse transaction properties are read"() {
        when:
        def document = getDocument("nemmsats_samples_r39_p1/NEMMSATS_MDMReportRM16_r39_p1.xml")

        then:
        document.from == "NEMMCO"
        document.fromDescription == "NEMMCO"
        document.to == "ENGYAUST"
        document.toDescription == "Energy Australia"
        document.messageID == "NEMMCO-MSG-1143"
        document.messageDate == LocalDateTime.of(2001, 11, 12, 12, 35, 4) // TODO: message is +00:00; convert to AEST for market time
        document.transactionGroup == TransactionGroup.MDMT
        document.priority == Priority.High
        document.securityContext == "NEMMCOBATCH"
        document.market == null

        def transactions = document.transactions
        transactions.size() == 1
        ReportResponse txn = transactions[0]
        txn.transactionID == "MDMT-1143"
        txn.transactionDate == LocalDateTime.of(2001, 11, 12, 12, 35, 4) // TODO: transaction is +00:00; convert to AEST for market time
        txn.initiatingTransactionID == "136"
        txn.transactionName == "ReportResponse"
        txn.reportParameters == [
                ReportName: "Level1SettlementReconciliation",
                SettlementCase: "24",
                FromDate: "0001-09-23",
                ToDate: "0001-09-30",
                LastSequenceNumber: "0",
                SettlementRun: "PRELIMINARY",
        ]

        txn.resultBlock == null

        CSVReportFormat results = txn.reportResults
        results.csvData.startsWith("TNI,DataType,FRMP,")
        results.csvData.length() == 38404

        def events = txn.events
        events.size() == 1
        def event = events[0]
        event.eventSeverity == EventSeverity.Information
        event.code == 0
        event.codeDescription == null
        event.keyInfo == null
        event.context == null
        event.explanation == null
    }
}
