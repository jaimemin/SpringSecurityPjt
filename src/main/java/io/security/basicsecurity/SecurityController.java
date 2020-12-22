package io.security.basicsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(HttpSession session) {
        // SecurityContextHolder에서 꺼내오는 방식
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        // 세션에서 꺼내오는 방식
        SecurityContext context
                = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication2 = context.getAuthentication();

        return "home";
    }

    @GetMapping("/thread")
    public String thread() {
        // Thread 간 SecurityContext 확인
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication
                                = SecurityContextHolder.getContext().getAuthentication();
                    }
                }
        ).start();

        return "thread";
    }
}
