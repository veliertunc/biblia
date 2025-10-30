package dev.veliertunc.biblia.user

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface UserMapper {

    fun toResponse(entity: User): UserResponse

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", defaultValue = "true")
    @Mapping(target = "roles", ignore = true)   // set roles manually if needed
    fun fromCreateRequest(req: CreateUserRequest): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(req: UpdateUserRequest, @MappingTarget entity: User)
}