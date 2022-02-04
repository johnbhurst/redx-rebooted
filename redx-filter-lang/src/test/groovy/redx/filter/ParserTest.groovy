// Copyright 2022 John Hurst
// 2022-01-20
// John Hurst

package redx.filter

import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

import static redx.filter.TokenType.TEXT

class ParserTest extends Specification {

    private static Token token(TokenType tokenType, String lexeme) {
        new Token(tokenType, lexeme, null, 1)
    }

    private static Token token(TokenType tokenType, String lexeme, Object literal) {
        new Token(tokenType, lexeme, literal, 1)
    }

    private static Token text(String s) {
        token(TEXT, s)
    }

    private static Expr parse(String input) {
        List<Token> tokens = new Scanner(input).scanTokens()
        return new Parser(tokens).parse()
    }

    private static Expr and(Expr... exprs) {
        new Expr.And(exprs as List)
    }

    private static Expr or(Expr... exprs) {
        new Expr.Or(exprs as List)
    }

    private static Expr composite(Expr expr) {
        new Expr.Composite(expr)
    }

    private static Expr binary(String variable, BinaryOperator operator, Value value) {
        new Expr.BinaryExpression(variable, operator, value)
    }

    private static Expr not(Expr expr) {
        new Expr.Not(expr)
    }

    private static Expr stringRestriction(String val) {
        new Expr.StringRestriction(val)
    }

    private static Value stringValue(String val) {
        new Value.StringValue(val)
    }

    private static Value intValue(Integer val) {
        new Value.IntegerValue(val)
    }

    private static Value dateValue(String val) {
        new Value.DateValue(LocalDate.parse(val))
    }

    private static Value dateTimeValue(String val) {
        new Value.DateTimeValue(LocalDateTime.parse(val))
    }

    private static Expr has(Expr expr, String key) {
        null // not required
    }

    private static Value function(String name, Value... args) {
        null // not required
    }

    // See https://google.aip.dev/assets/misc/ebnf-filtering.txt for these examples.
    // We don't support these full examples from Google. We support a subset of the filter language adapted for Redx's requirements; see below.
    @Ignore
    def "Parser parses Google sample input"() {
        expect:
        parse(input) == result

        where:
        input                                         || result
        "a b c d"                                      | and(stringRestriction("a"), stringRestriction("b"), stringRestriction("c"), stringRestriction("d"))
        "a AND b AND c AND d"                          | and(stringRestriction("a"), stringRestriction("b"), stringRestriction("c"), stringRestriction("d"))
        "a b AND c AND d"                              | and(stringRestriction("a"), stringRestriction("b"), stringRestriction("c"), stringRestriction("d"))
        "(a b) AND c AND d"                            | and(composite(and(stringRestriction("a"), stringRestriction("b"))), stringRestriction("c"), stringRestriction("d"))
        "New York Giants OR Yankees"                   | and(stringRestriction("New"), stringRestriction("York"), or(stringRestriction("Giants"), stringRestriction("Yankees")))
        "New York (Giants OR Yankees)"                 | and(stringRestriction("New"), stringRestriction("York"), composite(or(stringRestriction("Giants"), stringRestriction("Yankees"))))
        "a < 10 OR a >= 100"                           | or(binary("a", BinaryOperator.LESS_THAN, intValue(10)), binary("a", BinaryOperator.GREATER_EQUALS, intValue(100)))
        "NOT (a OR b)"                                 | not(or(stringRestriction("a"), stringRestriction("b")))
        "-file:\".java\""                              | not(stringRestriction("file:\".java\""))
        "-30"                                          | not(stringRestriction("30"))
        "package=com.google"                           | binary("package", BinaryOperator.EQUALS, stringValue("com.google"))
        "msg != 'hello'"                               | binary("msg", BinaryOperator.NOT_EQUALS, stringValue("hello"))
//        "1 > 0"                                        | binary(value("1"), BinaryOperator.GREATER_THAN, value("0"))
//        "2.5 >= 2.4"                                   | binary(value("2.5"), BinaryOperator.GREATER_EQUALS, value("2.4"))
//        "yesterday < request.time"                     | binary(variable("yesterday"), BinaryOperator.LESS_THAN, value("request.time"))
//        "experiment.rollout <= cohort(request.user)"   | binary("experiment.rollout", BinaryOperator.LESS_EQUALS, function("cohort", stringValue("request.user")))
//        "map:key"                                      | has(variable("map"), "key")
        "prod"                                         | stringRestriction("prod")
//        "expr.type_map.1.type"                         | variable("expr.type_map.1.type")
//        "regex(m.key, '^.*prod.*\$')"                  | function("regex", variable("m.key"), value("^.*prod.*\$"))
//        "math.mem('30mb')"                             | function("math.mem", value("30mb"))
//        "(msg.endsWith('world') AND retries < 10)"     | composite(sequence(function("msg.endsWith", value("world"))), binary(variable("retries"), BinaryOperator.LESS_THAN, value("10")))
    }

    def "Parser parses more limited redx-specific input"() {
        expect:
        parse(input) == result

        where:
        input                                                                            || result
        "6012345678"                                                                      | stringRestriction("6012345678")
//        "6012345678 CATS"                                                                 | sequence(value("6012345678"), value("CATS"))
        "6012345678 AND CATS"                                                             | and(stringRestriction("6012345678"), stringRestriction("CATS"))
        "fileName=NEMMSATS_MDMReportRM16_r39_p1.xml"                                      | binary("fileName", BinaryOperator.EQUALS, stringValue("NEMMSATS_MDMReportRM16_r39_p1.xml"))
        "transactionGroup=CATS"                                                           | binary("transactionGroup", BinaryOperator.EQUALS, stringValue("CATS"))
        "transactionType=CATSNotification"                                                | binary("transactionType", BinaryOperator.EQUALS, stringValue("CATSNotification"))
//        "6012345678 transactionGroup='CATS'"                                              | sequence(value("6012345678"), binary(variable("transactionGroup"), BinaryOperator.EQUALS, value("CATS")))
        "transactionGroup=CATS AND 6012345678"                                            | and(binary("transactionGroup", BinaryOperator.EQUALS, stringValue("CATS")), stringRestriction("6012345678"))
        "6012345678 OR 6087654321"                                                        | or(stringRestriction("6012345678"), stringRestriction("6087654321"))
        "market != \"NEM\""                                                               | binary("market", BinaryOperator.NOT_EQUALS, stringValue("NEM"))
        "-6012345678"                                                                     | not(stringRestriction("6012345678"))
        "NOT market=\"NEM\""                                                              | not(binary("market", BinaryOperator.EQUALS, stringValue("NEM")))
        "fileSize > 1000"                                                                 | binary("fileSize", BinaryOperator.GREATER_THAN, intValue(1000))
        "uploadDate >= 2022-01-01T00:00:00 AND uploadDate < 2022-02-01"                   | and(binary("uploadDate", BinaryOperator.GREATER_EQUALS, dateTimeValue("2022-01-01T00:00:00")), binary("uploadDate", BinaryOperator.LESS_THAN, dateValue("2022-02-01")))
        "txn.transactionID = 'transaction0001'"                                           | binary("txn.transactionID", BinaryOperator.EQUALS, stringValue("transaction0001"))
        "txn.transactionDate >= 2022-01-01T00:00:00 AND txn.transactionDate < 2022-02-01" | and(binary("txn.transactionDate", BinaryOperator.GREATER_EQUALS, dateTimeValue("2022-01-01T00:00:00")), binary("txn.transactionDate", BinaryOperator.LESS_THAN, dateValue("2022-02-01")))
//        "transactionGroup=CATS 6012345678 OR 6087654321"                                  | sequence(binary(variable("transactionGroup"), BinaryOperator.EQUALS, value("CATS")), factor(value("6012345678"), value("6087654321")))
//        "(transactionGroup=CATS 6012345678) OR 6087654321"                                | factor(composite(sequence(binary(variable("transactionGroup"), BinaryOperator.EQUALS, value("CATS")), value("6012345678")), value("6087654321")))
        "MeterDataNotification.VersionHeader=NEM12"                                       | binary("MeterDataNotification.VersionHeader", BinaryOperator.EQUALS, stringValue("NEM12"))
        "ReportResponse.ReportParameters.ReportName=Level1SettlementReconciliation"       | binary("ReportResponse.ReportParameters.ReportName", BinaryOperator.EQUALS, stringValue("Level1SettlementReconciliation"))
        "ReportResponse.ReportParameters.SettlementCase=8885"                             | binary("ReportResponse.ReportParameters.SettlementCase", BinaryOperator.EQUALS, intValue(8885))
        "ReportResponse.ReportParameters.SettlementRun=PRELIMINARY"                       | binary("ReportResponse.ReportParameters.SettlementRun", BinaryOperator.EQUALS, stringValue("PRELIMINARY"))
    }

}
