package com.example.mysqlexample.config;

import com.example.mysqlexample.filter.JwtAuthFilter;
import com.example.mysqlexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Bu anotasyon, bir sınıfın yapılandırma sınıfı olduğunu belirtir
@EnableWebSecurity //Bu anotasyon, Spring Security'nin web güvenliği özelliklerini etkinleştirir
@EnableMethodSecurity //Bu anotasyon, Spring Security ile metod seviyesinde yetkilendirme özelliklerini etkinleştirir
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;

    public SecurityConfig(JwtAuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean //UserService kullanılan yerlere UserDetailsService olarak enjekte etmek için.
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/users/{userId}/todos/{todoId}").authenticated()
                        .requestMatchers("/auth/welcome", "/auth/register", "/auth/login","/users/").permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

//Yukarıdaki kod örneğinde, Spring Boot uygulamasının güvenlik (security) konfigürasyonunu tanımlamak ve yönetmek amacıyla @Configuration anotasyonu kullanılmıştır. Bu sınıfın adı "SecurityConfig" ve @Configuration anotasyonu sayesinde bu sınıf Spring'in yapılandırma sınıflarından biri olarak tanımlanmıştır.
//@Bean anotasyonları ise bu yapılandırma sınıfının içinde Spring konteynerine yönetilen nesneleri (bileşenleri) tanımlamak için kullanılmıştır. Bu nesneleri yönetilen bileşenler haline getirerek Spring'in IoC (Inversion of Control) prensiplerini kullanmış olursunuz.
//Özellikle, aşağıdaki amaçlarla @Bean anotasyonları kullanılmıştır:
//userDetailsService() metodu: Bu metot, UserDetailsService tipinde bir nesne döndürür. @Bean anotasyonu sayesinde bu nesnenin Spring konteyner tarafından yönetilen bir bileşen olduğunu belirtirsiniz.
//securityFilterChain(HttpSecurity http) metodu: Bu metot, güvenlik filtre zincirini konfigüre eder ve SecurityFilterChain tipinde bir nesne döndürür. Bu nesne de @Bean anotasyonuyla Spring tarafından yönetilen bir bileşen olarak işaretlenir.
//passwordEncoder() metodu: Bu metot, şifreleme işlemleri için PasswordEncoder tipinde bir nesne döndürür. Yine @Bean anotasyonuyla Spring tarafından yönetilen bir bileşen olarak işaretlenir.
//authenticationProvider() metodu: Bu metot, kimlik doğrulama sağlayıcısını (authentication provider) konfigüre eder ve AuthenticationProvider tipinde bir nesne döndürür. Bu nesne de @Bean anotasyonuyla Spring tarafından yönetilen bir bileşen olarak işaretlenir.
//authenticationManager(AuthenticationConfiguration config) metodu: Bu metot, kimlik doğrulama yöneticisini (authentication manager) oluşturur ve AuthenticationManager tipinde bir nesne döndürür. Yine @Bean anotasyonuyla Spring tarafından yönetilen bir bileşen olarak işaretlenir.
//@Bean anotasyonları ile işaretlenen metotlar, bu metotların döndürdüğü nesnelerin Spring tarafından yönetilen bileşenler olduğunu belirtir. Bu sayede Spring konteyneri, bu bileşenleri oluşturabilir, enjekte edebilir ve gerektiğinde kullanabilir hale getirir.