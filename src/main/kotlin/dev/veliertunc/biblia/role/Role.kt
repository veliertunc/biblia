package dev.veliertunc.biblia.role

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.user.User

import jakarta.persistence.*
import lombok.*

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Role(
    @Column(nullable = false, unique = true)
    var name: String,               // e.g. "ROLE_USER", "ROLE_ADMIN"

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    var users: MutableSet<User> = mutableSetOf()
) : BaseEntity()
