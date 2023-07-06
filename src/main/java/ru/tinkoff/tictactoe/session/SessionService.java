package ru.tinkoff.tictactoe.session;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, UUID botId);

    StateOfSession getCurrentStateOfSession(UUID sessionId);

    StateOfSession getStateOfSessionByTurn(UUID sessionId, Integer turn);

    List<StateOfSession> getSessionsByIsActive(Boolean isActive);
}
