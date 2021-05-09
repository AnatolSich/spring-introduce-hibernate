package com.example.springintroducehibernate.service.impl;

import com.example.springintroducehibernate.dao.impl.TicketDAO;
import com.example.springintroducehibernate.model.Event;
import com.example.springintroducehibernate.model.Ticket;
import com.example.springintroducehibernate.model.User;
import com.example.springintroducehibernate.model.impl.TicketImpl;
import com.example.springintroducehibernate.service.EventService;
import com.example.springintroducehibernate.service.TicketService;
import com.example.springintroducehibernate.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    private TicketDAO ticketDAO;
    private UserAccountService userService;
    private EventService eventService;
    private UserAccountService userAccountService;

    @Autowired
    public TicketServiceImpl(TicketDAO ticketDAO, UserAccountService userService, EventService eventService, UserAccountService userAccountService) {
        this.ticketDAO = ticketDAO;
        this.userService = userService;
        this.eventService = eventService;
        this.userAccountService = userAccountService;
    }

    @Transactional
    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        TicketImpl ticket = new TicketImpl(new Date().getTime(), eventId, userId, category, place);
        ticketDAO.save(ticket);
        userService.withdrawAccount(userId, eventService.getEventById(eventId).getPrice());
        log.info("book ticket for event-" + eventId + ", place-" + place);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public void cancelTicket(long ticketId) {
        log.info("Ticket s% canceled", ticketId);
        ticketDAO.delete(ticketId);
    }

    @Override
    public void saveAllTickets(List<Ticket> list) {
        ticketDAO.saveAllTickets(list);
    }

}
