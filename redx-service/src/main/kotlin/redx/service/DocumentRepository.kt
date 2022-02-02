package redx.service

import org.springframework.data.repository.PagingAndSortingRepository

interface DocumentRepository: PagingAndSortingRepository<Document, String>
