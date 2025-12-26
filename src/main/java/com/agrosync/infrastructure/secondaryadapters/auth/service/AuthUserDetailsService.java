package com.agrosync.infrastructure.secondaryadapters.auth.service;

import com.agrosync.domain.enums.auth.RolEnum;
import com.agrosync.application.secondaryports.entity.auth.AuthUserEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.repository.AuthUserRepository;
import com.agrosync.crosscutting.helpers.JwtHelper;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public AuthUserDetailsService(AuthUserRepository authUserRepository,
                                  PasswordEncoder passwordEncoder,
                                  JwtHelper jwtHelper,
                                  SuscripcionExisteRule suscripcionExisteRule) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUserEntity user = authUserRepository.findByEmail(email)
                .filter(AuthUserEntity::isActivo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    public AuthResponse register(String email, String password, RolEnum rol, UUID suscripcionId) {
        if (authUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        RolEnum rolAsignado = rol != null ? rol : RolEnum.EMPLEADO;

        if (rolAsignado != RolEnum.SUPER_ADMINISTRADOR) {
            suscripcionExisteRule.validate(suscripcionId);
        } else {
            suscripcionId = null;
        }

        AuthUserEntity entity = AuthUserEntity.create()
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setRol(rolAsignado);

        if (suscripcionId != null) {
            entity.setSuscripcion(SuscripcionEntity.create(suscripcionId));
        }

        authUserRepository.save(entity);

        return buildAuthResponse(entity);
    }

    public AuthResponse login(String email, String password) {
        AuthUserEntity user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario o contraseña inválida"));

        if (!user.isActivo()) {
            throw new UsernameNotFoundException("El usuario no se encuentra activo");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña inválida");
        }

        return buildAuthResponse(user);
    }

    public AuthUserEntity getLoggedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UsernameNotFoundException("No hay usuario autenticado");
        }

        return authUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario autenticado no encontrado"));
    }

    public void actualizarEstadoUsuario(UUID userId, boolean activo) {
        AuthUserEntity user = authUserRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        user.setActivo(activo);
        authUserRepository.save(user);
    }

    private AuthResponse buildAuthResponse(AuthUserEntity user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

        String token = jwtHelper.createToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setRol(user.getRol().name());
        return authResponse;
    }
}
