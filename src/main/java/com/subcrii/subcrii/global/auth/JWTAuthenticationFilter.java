package com.subcrii.subcrii.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailService customUserDetailService;

    private static final PathPatternRequestMatcher.Builder PATH =
            PathPatternRequestMatcher.withDefaults();

    private static final List<RequestMatcher> WHITELIST = List.of(
            PATH.matcher(HttpMethod.POST, "/signup"),
            PATH.matcher(HttpMethod.POST, "/login"),

            PATH.matcher("/creators/main/**"),

            PATH.matcher("/v3/api-docs/**"),
            PATH.matcher("/swagger-ui/**"),
            PATH.matcher("/swagger-ui.html"),

            PATH.matcher("/error"),

            // CORS preflight
            PATH.matcher(HttpMethod.OPTIONS, "/**")
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return WHITELIST.stream().anyMatch(m -> m.matches(request));
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (!tokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String role = tokenProvider.getRoleFromToken(token);
        String memberId = tokenProvider.getMemberIdFromToken(token);

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        User principal = new User(memberId, "", authorities);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}