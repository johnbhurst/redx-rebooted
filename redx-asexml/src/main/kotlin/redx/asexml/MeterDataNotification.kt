// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class MeterDataNotification(element: Element, document: Document): Transaction(element, document) {

    val meterDataFileType: MeterDataFileType by lazy {
        if (node("CSVConsumptionData") != null) MeterDataFileType.NEM13
        else if (node("CSVIntervalData") != null) MeterDataFileType.NEM12
        else throw IllegalStateException("Neither CSVConsumptionData not CSVIntervalData present; cannot determine MeterDataFileType")
    }

    val csvConsumptionData: String? by lazy { text("CSVConsumptionData") }
    val csvIntervalData: String? by lazy { text("CSVIntervalData") }
    val csvProfileData: String? by lazy { text("CSVProfileData") }
    val participantRole: String? by lazy { text("ParticipantRole/Role") }
}
