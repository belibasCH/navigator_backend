package com.example.navigator_backend.repository;

import com.example.navigator_backend.domain.Navigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class NavigatorRepository {
    private Logger log = LoggerFactory.getLogger(NavigatorRepository.class);
    private List<Navigator> navigators = new ArrayList<Navigator>();

    public Navigator savePoll(Navigator p) {
        Navigator poll = new Navigator(Integer.toString(navigators.size()), p.question(), p.answers());
        navigators.add(poll);
        log.debug("Successfully added poll[{}] to repository", poll.id());
        return poll;
    }

    public List<Navigator> getAllNavigators() {
        return navigators;
    }

    public Navigator getActNavigator() {
        Navigator last = null;
        if (navigators.size() > 0) {
            last = navigators.get(navigators.size() - 1);
        }
        return last;
    }

    public void reset() {
        this.navigators = new ArrayList<Navigator>();
        log.info("Reset successfully");
    }

}