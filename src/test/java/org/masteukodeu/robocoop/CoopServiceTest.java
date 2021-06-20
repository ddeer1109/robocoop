package org.masteukodeu.robocoop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.masteukodeu.robocoop.db.*;
import org.masteukodeu.robocoop.model.Clock;
import org.masteukodeu.robocoop.model.CoopService;
import org.masteukodeu.robocoop.model.Round;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoopServiceTest {


    @Test
    public void orderShouldBeBlockedWhenLastOrderDateIsLessThanTwoDays(){

        JdbcRoundDAO newRound = new RoundDAOTestClass(0);
        Clock clock = new Clock();

        CoopService sut = new CoopService(
                null,
                null,
                null,
                newRound,
                clock,
                null,
                null
        );

        Assertions.assertTrue(sut.isOrderingBlocked(), "order is not blocked");

    }

    @Test
    public void orderShouldNotBeBlockedWhenLastOrderDateIsMoreThanTwoDays(){

        RoundDAO roundDAO = mock(RoundDAO.class);
        when(roundDAO.current()).thenReturn(new Round("", "", LocalDate.parse("2000-01-10")));

        Clock clock = mock(Clock.class);
        when(clock.now()).thenReturn(LocalDateTime.parse("2000-01-08T19:59:59"));

        CoopService sut = new CoopService(
                null,
                null,
                null,
                roundDAO,
                clock,
                null,
                null
        );
        Assertions.assertFalse(sut.isOrderingBlocked(), "order should not be blocked");

    }

    class RoundDAOTestClass extends JdbcRoundDAO {
        int daysDelta;

        public RoundDAOTestClass(int daysDelta) {
            super(null);
            this.daysDelta = daysDelta;
        }

        @Override
        public Round current() {
            return new Round("1", "test", LocalDate.now().plusDays(daysDelta));
        }
    }
}
