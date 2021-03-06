package com.tsbonev.Controller;

import com.tsbonev.Repository.EventRepository;
import com.tsbonev.Entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    private String message = "Hello world";

    @GetMapping("/hello")
    public ModelAndView welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return new ModelAndView("hello");
    }

    @GetMapping(path = "/add")
    public ModelAndView addNewEvent (@RequestParam String name,
                                             @RequestParam String location){
        Event e = new Event();
        e.setName(name);
        e.setLocation(location);
        e.setStartDate(LocalDateTime.from(LocalDateTime.now()));
        e.setEndDate(LocalDateTime.from(LocalDateTime.now()).plusDays(6).plusHours(8));
        eventRepository.save(e);
        return new ModelAndView("add");
    }

    @RequestMapping(path = "/delete/{id}")
    public ModelAndView deleteEvent(@PathVariable(value = "id") long id){
        eventRepository.deleteById(id);
        return new ModelAndView("redirect:/all");
    }

    @RequestMapping(path = "/view/{id}")
    public ModelAndView viewEvent(@PathVariable(value = "id") long id){

        Event event = eventRepository.findById(id).get();

        return new ModelAndView("view").addObject("event", event);


    }

    @GetMapping(path = "/all")
    public ModelAndView getAllEvents(Map<String, Object> model){

        Iterable<Event> events = eventRepository.findAll();

        model.put("events", events);

        return new ModelAndView("all");
    }

}
