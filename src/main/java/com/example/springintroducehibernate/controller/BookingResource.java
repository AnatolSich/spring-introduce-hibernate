package com.example.springintroducehibernate.controller;

import com.example.springintroducehibernate.facade.BookingFacade;
import com.example.springintroducehibernate.model.Event;
import com.example.springintroducehibernate.model.Ticket;
import com.example.springintroducehibernate.model.User;
import com.example.springintroducehibernate.model.impl.EventImpl;
import com.example.springintroducehibernate.model.impl.TicketImpl;
import com.example.springintroducehibernate.model.impl.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class BookingResource extends ParameterizableViewController {

    private Logger log = LoggerFactory.getLogger(BookingResource.class);

    @Autowired
    private BookingFacade bookingFacade;

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody UserImpl user) {
        bookingFacade.createUser(user);
        return user;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public Event addEvent(@RequestBody EventImpl event) {
        bookingFacade.createEvent(event);
        return event;
    }

    @RequestMapping(value = "/ticket/book", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public Ticket addEvent(@RequestBody TicketImpl ticket) {
        bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        return ticket;
    }


    @RequestMapping(value = "user/refill", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public double refillAccount(@PathVariable long id, @PathVariable double money) {
        bookingFacade.refillAccount(id, money);
        return bookingFacade.getScore(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody UserImpl user) {
        return bookingFacade.updateUser(user);
    }

    @RequestMapping(value = "/event", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public Event updateEvent(@RequestBody EventImpl event) {
        return bookingFacade.updateEvent(event);
    }

    @RequestMapping(value = "/events/{title}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<Event> getEventsByTitle(@PathVariable String title) {
        return bookingFacade.getEventsByTitle(title, 10, 0);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Event getEventById(@PathVariable long id) {
        return bookingFacade.getEventById(id);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public void deleteEvent(@PathVariable long id) {
        log.info("Remove event id:{id}", id);
        bookingFacade.deleteEvent(id);
    }

    @RequestMapping(value = "/user/{email}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public User getUserByEmail(@PathVariable String email) {
        return bookingFacade.getUserByEmail(email);
    }

    @RequestMapping(value = "/user/{id}/tickets", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<Ticket> getBookedTicketsByUserId(@PathVariable long id) {
        return bookingFacade.getBookedTickets(bookingFacade.getUserById(id), 10, 0);
    }

    @RequestMapping(value = "/event/{id}/tickets", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<Ticket> getBookedTicketsByEventId(@PathVariable long id) {
        return bookingFacade.getBookedTickets(bookingFacade.getEventById(id), 10, 0);
    }

}
