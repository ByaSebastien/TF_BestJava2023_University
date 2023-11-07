package be.bstorm.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService detailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService detailsService) {
        this.jwtUtil = jwtUtil;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String method = request.getMethod();
        if (!method.equals("OPTIONS")) {
            final String authorization = request.getHeader("Authorization");

            System.out.println(method);
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.replace("Bearer ", "");
                String username = jwtUtil.getUsernameFromToken(token);

                UserDetails user = this.detailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(upat); //Après cette ligne ci, je suis connecté
            }
        }

        filterChain.doFilter(request, response);
    }
}
