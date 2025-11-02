package dev.veliertunc.biblia.entry

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class EntryService(
    private val entryRepo: EntryRepository,
    private val entryMapper: EntryMapper
) {

    fun toResponse(entry: Entry) = entryMapper.toResponse(entry)

    fun getAll() = entryRepo.findAll()

    fun getById(id: UUID) = entryRepo.findById(id)
        .orElseThrow { EntityNotFoundException("Entry $id not found") }

    @Transactional
    fun create(req: CreateEntryRequest): Entry {
        val entry = entryMapper.fromCreateRequest(req)

        return entryRepo.save(entry)
    }

    @Transactional
    fun update(id: UUID, req: UpdateEntryRequest): Entry {
        val entry = entryRepo.findById(id)
            .orElseThrow { EntityNotFoundException("Entry $id not found") }

        entryMapper.updateEntityFromDto(req, entry)

        return entryRepo.save(entry)
    }

    @Transactional
    fun delete(id: UUID) {
        if (!entryRepo.existsById(id))
            throw EntityNotFoundException("Entry $id not found")
        entryRepo.deleteById(id)
    }
}