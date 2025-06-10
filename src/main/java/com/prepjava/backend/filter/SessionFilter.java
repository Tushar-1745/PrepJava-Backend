// package com.prepjava.backend.filter;

// import java.io.IOException;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.prepjava.backend.service.SessionService;

// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class SessionFilter implements Filter {

//     @Autowired
//     private SessionService sessionService;

//     private static final List<String> PUBLIC_ENDPOINTS = List.of(
//         "/api/signup", "/api/login", "/api/solvedproblems/submit", "/api/solvedproblems/getsolvedproblems"
//     );

//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//             throws IOException, ServletException {
//         HttpServletRequest httpRequest = (HttpServletRequest) request;
//         HttpServletResponse httpResponse = (HttpServletResponse) response;

//         String path = httpRequest.getRequestURI();

//         // Allow public endpoints without session validation
//         if (PUBLIC_ENDPOINTS.contains(path)) {
//             chain.doFilter(request, response);
//             return;
//         }

//         // Allow preflight requests for CORS
//         if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//             httpResponse.setStatus(HttpServletResponse.SC_OK);
//             return;
//         }

//         // Validate sessionId from cookies
//         Cookie[] cookies = httpRequest.getCookies();
//         if (cookies != null) {
//             for (Cookie cookie : cookies) {
//                 if ("sessionId".equals(cookie.getName())) {
//                     String sessionId = cookie.getValue();
//                     if (sessionService.isSessionValid(sessionId)) {
//                         chain.doFilter(request, response); // Continue if valid
//                         return;
//                     }
//                 }
//             }
//         }

//         // Unauthorized if session is invalid
//         httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         httpResponse.setContentType("application/json");
//         httpResponse.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing session.\"}");
//     }
// }
