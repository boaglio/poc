package com.boaglio.poc.grade;

import net.jqwik.api.*;
import net.jqwik.api.constraints.FloatRange;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PassingGradesPBTest {

    private final PassingGrade pg = new PassingGrade();

    @Property
    void fail(
            @ForAll
            @FloatRange(min = 1f, max = 5f, maxIncluded = false)
            float grade) {

        assertThat(pg.passed(grade)).isFalse();

    }

    @Property
    void pass(
            @ForAll
            @FloatRange(min = 5f, max = 10f, maxIncluded = true)
            float grade) {
        assertThat(pg.passed(grade)).isTrue();
    }

    @Property
    void invalid(
            @ForAll("invalidGrades")
            float grade) {

        assertThatThrownBy(() -> {
            pg.passed(grade);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Provide
    private Arbitrary<Float> invalidGrades() {
        return Arbitraries.oneOf(
                Arbitraries.floats().lessThan(1f),
                Arbitraries.floats().greaterThan(10f)
        );
    }

}