package cz.muni.fi.pv168.project;

import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.assertj.core.api.Assertions.assertThat;

final class MainTest {

    @Test
    void validateEntities() {
        Result result = JUnitCore.runClasses(ValidatorTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        assertThat(result.wasSuccessful()).isTrue();
    }

    @Test
    void unitTesting() {
        Result result = JUnitCore.runClasses(UnitTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        assertThat(result.wasSuccessful()).isTrue();
    }
}
