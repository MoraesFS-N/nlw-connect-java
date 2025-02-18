package br.com.nlw.events.service;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepo repository;

    public Event save(Event event) {
        String prettyName = event.getTitle().toLowerCase().replace(' ','-');

        event.setPrettyName(prettyName);

        return repository.save(event);
    }

    public List<Event> getAll() {
        return (List<Event>) repository.findAll();
    }

    public Event getByPrettyName(String prettyName) {
        return repository.findByPrettyName(prettyName);
    }
}
