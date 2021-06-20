package org.masteukodeu.robocoop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.masteukodeu.robocoop.db.RoundDAO;
import org.masteukodeu.robocoop.model.Clock;
import org.masteukodeu.robocoop.model.CoopService;
import org.masteukodeu.robocoop.model.Round;

import java.time.LocalDate;

public class CoopServiceTest {


    @Test
    public void orderShouldBeBlocked(){


        Round testRound = new Round("1", "test", LocalDate.now());
        RoundDAO newRound = new RoundDAO();
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
}
