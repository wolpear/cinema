package pl.karczewski.cinema.infrastructure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.client.ClientRepository

@Service
class ClientDetailsService(val clientRepository: ClientRepository) : UserDetailsService {

    // we use e-mail as a username
    override fun loadUserByUsername(username: String?): UserDetails {
        val client = clientRepository.findByEmail(username!!)
            ?: throw UsernameNotFoundException("User with e-mail $username not found!")
        return ClientDetails(client)
    }
}

data class ClientDetails(
    val client: Client
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("USER"))

    override fun getPassword(): String = client.password

    override fun getUsername(): String = client.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
