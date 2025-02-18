package br.com.nlw.events.controller;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService service;


    @PostMapping("/events")
    public Event create(@RequestBody Event event) {
        return service.save(event);
    }

    @GetMapping("/events")
    public List<Event> list() {
        return service.getAll();
    }

    @GetMapping("/events/{prettyName}")
    public Event list(@PathVariable String prettyName) {
        return service.getByPrettyName(prettyName);
    }

}
