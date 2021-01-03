package pl.karczewski.cinema.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.karczewski.cinema.domain.client.Client
import java.io.IOException
import java.lang.RuntimeException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.context.SecurityContextHolder

import javax.servlet.ServletException

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.http.Cookie

import org.springframework.web.util.WebUtils.getCookie
import pl.karczewski.cinema.common.Mapper
import pl.karczewski.cinema.domain.client.ClientCredentials



object SecurityConstants {
    const val SECRET = "SecretKeyToGenJWTs"
    const val EXPIRATION_TIME: Long = 864_000_000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val COOKIE_NAME = "access_token"
    const val SIGN_UP_URL = "/login"
}

class JWTAuthenticationFilter(authenticationManager: AuthenticationManager): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val client = Mapper.mapper.readValue(request?.inputStream, ClientCredentials::class.java)

            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    client.email,
                    client.password,
                    listOf()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val sub = (authResult?.principal as? ClientDetails)
        val token = JWT.create()
            .withSubject(sub?.username)
            .withClaim("name", sub?.client?.firstName + " " + sub?.client?.lastName)
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET))
        response?.addCookie(Cookie(SecurityConstants.COOKIE_NAME, token))
    }
}

class JWTAuthorizationFilter(authManager: AuthenticationManager?) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val cookie = getCookie(req, SecurityConstants.COOKIE_NAME)
        if (cookie != null && cookie.value?.startsWith(SecurityConstants.TOKEN_PREFIX)!!) {
            chain.doFilter(req, res)
            return
        }
        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = getCookie(request, SecurityConstants.COOKIE_NAME)?.value
        if (token != null) {
            val user: String = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET))
                .build()
                .verify(token)
                .subject

            return UsernamePasswordAuthenticationToken(user, null, listOf())
        }
        return null
    }
}