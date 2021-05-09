package com.example.springintroducehibernate.controller;

import com.example.springintroducehibernate.facade.BookingFacade;
import com.example.springintroducehibernate.model.Event;
import com.example.springintroducehibernate.util.exception_resolver.ErrorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class BookingController {

    private Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingFacade bookingFacade;

    @RequestMapping(value = "/events/{event}", method = RequestMethod.GET)
    public ModelAndView getEventsByEventTitle(@PathVariable String event) {
        List<Event> list = bookingFacade.getEventsByTitle(event, 10, 0);
        log.info("List of events with title: " + event + ", " + list);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("events", list);
        return modelAndView;
    }


    @ErrorView(value = "error", status = HttpStatus.GONE)
    @RequestMapping("/hello")
    public ModelAndView welcome() throws Exception {
        log.error("custom HandlerExceptionResolver test");
        throw new Exception();
    }

}
