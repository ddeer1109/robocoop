package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.RoundDAO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CurrentRoundInterceptor extends HandlerInterceptorAdapter {

    private final RoundDAO roundDAO;

    public CurrentRoundInterceptor(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("currentRound", roundDAO.current());
    }

}