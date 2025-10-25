data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String          // plain password – will be encoded in the service
)