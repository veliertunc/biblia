package dev.veliertunc.biblia.discussion

import dev.veliertunc.biblia.tag.Tag

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface DiscussionMapper {
    fun toResponse(entity: Discussion): DiscussionResponse

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun fromCreateRequest(req: CreateDiscussionRequest): Discussion

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateDiscussionRequest, @MappingTarget entity: Discussion)
}
