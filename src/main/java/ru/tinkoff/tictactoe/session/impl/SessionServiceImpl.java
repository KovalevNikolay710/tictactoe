package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
import ru.tinkoff.tictactoe.session.model.Session;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final GameService gameService;

    @Transactional
    @Override
    public Session createSession() {
        return sessionRepository.createSession();
    }

    @Transactional
    @Override
    public Figure registerBotInSession(UUID sessionId, UUID botId) {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session.getFirstBotId() == null) {
            sessionRepository.setFirstBotId(session.getId(), botId);
            return Figure.CROSS;
        }
        if (session.getSecondBotId() != null) {
            throw new SessionIsAlreadyFullException();
        }
        sessionRepository.setSecondBotId(session.getId(), botId);
        gameService.startGame(session.getId());
        return Figure.ZERO;
    }

    @Override
    public Session getSession(UUID sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    @Override
    public List<Session> getSessionsByIsActive(Boolean isActive) {
        log.info("SessionServiceImpl: getSessionsByIsActive stared with isActive = {}", isActive);
        if (isActive == null) {
            return sessionRepository.findAllSessions();
        }
        return sessionRepository.findSessionsByIsActive(isActive);
    }
}
