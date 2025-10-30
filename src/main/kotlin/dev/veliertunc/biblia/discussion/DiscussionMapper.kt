package dev.veliertunc.biblia.discussion

import dev.veliertunc.biblia.tag.Tag

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface DiscussionMapper {

    @Mapping(
        target = "tags",
        expression = "java(mapTagSetToStringSet(discussion.getTags()))"
    )
    fun toResponse(discussion: Discussion): DiscussionResponse

    @IterableMapping(elementTargetType = String::class)
    fun mapTagSetToStringSet(tags: Set<Tag>): Set<String>

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun fromCreateRequest(req: CreateDiscussionRequest): Discussion

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateDiscussionRequest, @MappingTarget discussion: Discussion)
}