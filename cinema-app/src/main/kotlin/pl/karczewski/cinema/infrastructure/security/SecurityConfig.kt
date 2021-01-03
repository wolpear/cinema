package pl.karczewski.cinema.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    val clientDetailsService: ClientDetailsService,
    val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(clientDetailsService)?.passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.cors()?.and()?.csrf()?.disable()
            ?.authorizeRequests()
            ?.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)?.permitAll()
            ?.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)?.permitAll()
            ?.antMatchers(HttpMethod.POST, "/api/v1/clients")?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.addFilter(JWTAuthenticationFilter(authenticationManager()))
            ?.addFilter(JWTAuthorizationFilter(authenticationManager()))
            // disable session creation by spring security
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}

@Bean
fun corsConfigurationSource(): CorsConfigurationSource {
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
    return source
}

@Configuration
class PassEncoderConfig() {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
