import java.time.Instant
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val username: String,
    val email: String,
    val enabled: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)