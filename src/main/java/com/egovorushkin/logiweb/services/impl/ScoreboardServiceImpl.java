package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.publisher.JmsPublisher;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardServiceImpl implements ScoreboardService {

    private final JmsPublisher jmsPublisher;

    public ScoreboardServiceImpl(JmsPublisher jmsPublisher) {
        this.jmsPublisher = jmsPublisher;
    }

    @Override
    public void updateScoreboard() {
        jmsPublisher.sendMessage("updated");
    }
}
