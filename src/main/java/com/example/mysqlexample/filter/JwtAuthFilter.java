package com.example.mysqlexample.filter;

import com.example.mysqlexample.service.JwtService;
import com.example.mysqlexample.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

//@Component:
//Spring framework'ün bir parçası olarak bağımlılık enjeksiyonu ile yönetilen bileşenlerin (component) tanımlanmasını sağlar.
//Bu anotasyon kullanılarak sınıfların Spring tarafından yönetilen bileşenler olduğu belirtilir.
//Yukarıdaki kodda JwtAuthFilter sınıfı @Component anotasyonu ile işaretlenmiş. Bu anotasyonun kullanılmasının temel amacı, JwtAuthFilter sınıfının Spring tarafından yönetilen bir bileşen olduğunu belirtmektir. Bu şekilde, Spring bu sınıfı otomatik olarak tespit edebilir, oluşturabilir ve gerekli bağımlılıkları enjekte edebilir.
//@Component anotasyonu ayrıca bu sınıfın diğer bileşenler tarafından kullanılabilir ve enjekte edilebilir hale gelmesini sağlar. Örneğin, JwtAuthFilter sınıfı JwtService ve UserService gibi bağımlılıklara ihtiyaç duyar. Bu bağımlılıklar da uygun şekilde @Autowired anotasyonu ile enjekte edilir.
//Sonuç olarak, @Component anotasyonu ile işaretlenen sınıflar, Spring tarafından yönetilen bileşenler olarak kabul edilir ve Spring'in IoC (Inversion of Control) prensiplerine uygun olarak oluşturulur, enjekte edilir ve kullanılabilir hale getirilir.