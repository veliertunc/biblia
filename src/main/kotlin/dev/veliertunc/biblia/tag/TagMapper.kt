package dev.veliertunc.biblia.tag

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface TagMapper {

    fun toResponse(tag: Tag): TagResponse

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun fromCreateRequest(req: CreateTagRequest): Tag

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateTagRequest, @MappingTarget tag: Tag)
}
