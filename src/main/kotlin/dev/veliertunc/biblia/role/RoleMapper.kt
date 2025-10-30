package dev.veliertunc.biblia.role

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface RoleMapper {

    fun toResponse(entity: Role): RoleResponse

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun fromCreateRequest(req: CreateRoleRequest): Role

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateRoleRequest, @MappingTarget entity: Role)
}