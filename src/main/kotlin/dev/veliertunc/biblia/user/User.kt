package dev.veliertunc.biblia.user

import dev.veliertunc.biblia.core.BaseEntity
import dev.veliertunc.biblia.role.Role

import jakarta.persistence.*
import lombok.*

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User(
    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var passwordHash: String,

    @Column(nullable = false)
    var enabled: Boolean = true,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
) : BaseEntity() {

    fun addRole(role: Role): Boolean {
        val added = roles.add(role)
        if (added)
            role.users.add(this)
        return added
    }

    fun removeRole(role: Role): Boolean {
        val removed = roles.remove(role)
        if (removed)
            role.users.remove(this)
        return removed
    }

}
