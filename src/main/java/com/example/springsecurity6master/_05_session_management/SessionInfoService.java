package com.example.springsecurity6master._05_session_management;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionInfoService {

    private final SessionRegistry sessionRegistry;

    public void sessionInfo() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal: principals) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInfo: sessions) {
                System.out.println("사용자: " + principal + ", 세션 ID: " + sessionInfo.getSessionId() + ", 최종 요청 시간: " + sessionInfo.getLastRequest());
            }
        }
    }
}
