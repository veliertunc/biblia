import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Role(
    @Column(nullable = false, unique = true)
    var name: String               // e.g. "ROLE_USER", "ROLE_ADMIN"
) : BaseEntity()
