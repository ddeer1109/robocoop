package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.JdbcRoundDAO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CurrentRoundInterceptor extends HandlerInterceptorAdapter {

    private final JdbcRoundDAO roundDAO;

    public CurrentRoundInterceptor(JdbcRoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        // TODO Fix the mapping of the interceptor, so e.g. static files are excluded
        if (modelAndView != null) {
            modelAndView.addObject("currentRound", roundDAO.current());
        }
    }

}