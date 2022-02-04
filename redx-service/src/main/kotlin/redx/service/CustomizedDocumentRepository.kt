// Copyright 2022 John Hurst
// John Hurst
// 2022-02-04

package redx.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface CustomizedDocumentRepository {
    fun findForFilter(filter: String): Iterable<Document>
    fun findForFilter(filter: String, sort: Sort): Iterable<Document>
    fun findForFilter(filter: String, pageable: Pageable): Page<Document>
}
