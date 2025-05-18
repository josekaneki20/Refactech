package org.dgtic.maquetado.security;

import org.dgtic.maquetado.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                       .requestMatchers(
                                "/guardarUsuario",
                                "/auth/**",
                                "/",
                                "/tema/**",
                                "/bootstrap/**",
                                "/imagenes/productos/**",
                                "/iconos/**",
                                "/js/**",
                               "/pagos/guardar",
                                "/catalogoPaginacion",
                               "cliente/Registro","cliente/guardarClientes","/productos/**"
                        ).permitAll()

                        .requestMatchers("/cliente/**","/trabajador/**","/almacen/**","/productos/**").hasRole("ADMIN")
                        .requestMatchers("/pedidos/admin","pedidos/admin/actualizar").hasRole("ADMIN")
                        .requestMatchers("/cliente/**","/auth/**","/carrito/**").hasRole("CLIENTE")
                        .requestMatchers("/pagos/**","/factura/**","/pedidos/mis-pedidos").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )

                /*.exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/auth/login"))
                )*/

                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JWT")
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

