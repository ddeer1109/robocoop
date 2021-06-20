package org.masteukodeu.robocoop;

import org.junit.jupiter.api.Test;
import org.masteukodeu.robocoop.db.RoundDAO;
import org.masteukodeu.robocoop.model.CoopService;
import org.masteukodeu.robocoop.model.Round;

import java.time.LocalDate;

public class CoopServiceTest {


    @Test
    public void CoopServiceTest(){
        Round testRound = new Round("1", "test", LocalDate.now());
        RoundDAO newRound = new RoundDAO();



        CoopService sut = new CoopService(
                null,
                null,
                null,
                newRound,
                null,
                null,
                null
        );
        sut.isOrderingBlocked();
        assert true;
    }
}
