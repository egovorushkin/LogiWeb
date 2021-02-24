package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.publisher.JmsPublisher;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.impl.ScoreboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    private static final String MSG_UPDATED = "updated";

    ScoreboardService scoreboardService;

    @Mock
    private JmsPublisher jmsPublisher;

    @BeforeEach
    public void init() {
        scoreboardService = new ScoreboardServiceImpl(jmsPublisher);
    }

    @Test
    @DisplayName("Test update scoreboard success")
    void testUpdateScoreboardSuccess() {
        scoreboardService.updateScoreboard();

        verify(jmsPublisher, times(1)).sendMessage(MSG_UPDATED);
    }
}
