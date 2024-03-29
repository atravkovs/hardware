package org.xapik.hardware.authentication;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;

  @Autowired
  public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain) throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);

      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        logger.debug("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        logger.debug("JWT Token has expired");
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
        && Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken))) {

      UserDetails userDetails = jwtTokenUtil.getUserDetailsFromToken(jwtToken);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());

      usernamePasswordAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    filterChain.doFilter(request, response);
  }
}
