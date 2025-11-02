package dev.veliertunc.biblia.entry

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface EntryMapper {
    fun toResponse(entity: Entry): EntryResponse

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun fromCreateRequest(req: CreateEntryRequest): Entry

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateEntryRequest, @MappingTarget entity: Entry)
}
