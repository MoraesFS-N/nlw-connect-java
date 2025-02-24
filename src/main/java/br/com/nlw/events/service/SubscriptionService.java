package br.com.nlw.events.service;


import br.com.nlw.events.dto.SubscriptionRankingByUserResponse;
import br.com.nlw.events.dto.SubscriptionRankingResponse;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepo;
import br.com.nlw.events.repository.SubscriptionRepo;
import br.com.nlw.events.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepo repository;

    @Autowired
    private EventRepo eventRepository;

    @Autowired
    private UserRepo userRepository;

    public SubscriptionResponse addSubscription(String eventName, User user, Integer userId) {

        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        User userAlreadyExists = userRepository.findByEmail(user.getEmail());
        if (userAlreadyExists == null) {
            userAlreadyExists = userRepository.save(user);
        }

        User userIndication = null;
        if (userId != null) {
            userIndication = userRepository.findById(userId).orElse(null);
            if (userIndication == null) {
                throw new UserIndicatorNotFoundException("User " + userId + " not exists");
            }
        }

        Subscription subAlreadyExists = repository.findByEventAndSubscriber(event, userAlreadyExists);
        if (subAlreadyExists != null) {
            throw new SubscriptionConflictException("User " + user.getName() + " is already subscribed to event " + eventName);
        }

        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(userAlreadyExists);
        subs.setIndication(userIndication);

        Subscription subSaved = repository.save(subs);

        return new SubscriptionResponse(subSaved.getSubscriptionNumber(), subSaved.getEvent().getPrettyName() + "/" + subSaved.getSubscriptionNumber());
    }

    public List<SubscriptionRankingResponse> getRanking(String prettyName) {

        Event event = eventRepository.findByPrettyName(prettyName);

        if (event == null) {
            throw new EventNotFoundException("Ranking " + prettyName + " not found");
        }

        return repository.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUserResponse getRankingByUser(String prettyName, Integer userId) {

        List<SubscriptionRankingResponse> ranking = getRanking(prettyName);

        SubscriptionRankingResponse rankingByUser = ranking.stream().filter(r -> r.userId().equals(userId)).findFirst().orElse(null);

        if (rankingByUser == null) {
            throw new UserIndicatorNotFoundException("User " + userId + " not found in ranking");
        }

        Integer position = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst().getAsInt();

        return new SubscriptionRankingByUserResponse(rankingByUser, position + 1);

    }
}
