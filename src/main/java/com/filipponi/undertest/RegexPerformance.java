package com.filipponi.undertest;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RegexPerformance {

    private static final Pattern PATTERN = Pattern.compile("\\s");

    private String value;

    @Setup
    public void setup() {
        value = RandomStringUtils.random(32, " abcdefghijklmnopqrstuvwxyz1234567890 ");
    }

    @Benchmark
    public void replaceAll_precompiled(Blackhole blackhole) {
        blackhole.consume(PATTERN.matcher(value));
    }
    @Benchmark
    public void replaceAll(Blackhole blackhole) {
        blackhole.consume(Pattern.compile("\\s").matcher(value));
    }

}




