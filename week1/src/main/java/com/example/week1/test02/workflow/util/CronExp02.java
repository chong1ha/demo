package com.example.week1.test02.workflow.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 크론 표현식을 처리하여, 다음 실행 시간을 계산
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/04 11:51 AM
 */
public class CronExp02 {

    /** 크론 표현식의 정규 표현식 패턴 (초 분 시 일 월 요일) */
    private static final Pattern CRON_PATTERN = Pattern.compile(
            "(\\*\\/\\d+|\\d+|\\*)\\s+(\\*\\/\\d+|\\d+|\\*)\\s+(\\*\\/\\d+|\\d+|\\*)\\s+(\\*\\/\\d+|\\d+|\\*)\\s+(\\*\\/\\d+|\\d+|\\*)\\s+(\\*\\/\\d+|\\d+|\\*)"
    );


    private final List<Integer> seconds;
    private final List<Integer> minutes;
    private final List<Integer> hours;
    private final List<Integer> daysOfMonth;
    private final List<Integer> months;
    private final List<Integer> daysOfWeek;


    /**
     * 크론 표현식에 따라 CronExp 객체 생성
     *
     * @param cronExp 크론 표현식 문자열
     * @throws IllegalArgumentException 크론 표현식이 유효하지 않을 때
     */
    public CronExp02(String cronExp) {
        Matcher matcher = CRON_PATTERN.matcher(cronExp);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid cron expression: " + cronExp);
        }

        seconds = parseCronPart(matcher.group(1));
        minutes = parseCronPart(matcher.group(2));
        hours = parseCronPart(matcher.group(3));
        daysOfMonth = parseCronPart(matcher.group(4));
        months = parseCronPart(matcher.group(5));
        daysOfWeek = parseCronPart(matcher.group(6));
    }

    /**
     * 크론 표현식의 각 부분을 파싱하여 정수 리스트로 변환
     *
     * @param cronPart 크론 표현식의 부분
     * @return 정수 리스트
     */
    private List<Integer> parseCronPart(String cronPart) {

        // 모든 값을 처리
        if (cronPart.equals("*")) {
            return IntStream.range(0, 60).boxed().collect(Collectors.toList());
        }

        if (cronPart.startsWith("*/")) {
            int interval = Integer.parseInt(cronPart.substring(2));
            return createRange(interval);
        }

        return Arrays.asList(cronPart.split(" ")).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> createRange(int interval) {

        return IntStream.range(0, 60)
                .filter(x -> x % interval == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * 현재 시간 기준으로 다음 실행 시간 계산
     *
     * @param currentTime 현재 시간 (밀리초)
     * @return 다음 실행 시간 (밀리초)
     */
    public long getNextTimeInMillis(long currentTime) {
        LocalDateTime now = Instant.ofEpochMilli(currentTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime nextTime = LocalDateTime.of(now.toLocalDate(), now.toLocalTime());

        // 초, 분, 시간만 고려 (요일, 월일은 제외)
        while (!isValid(nextTime)) {
            nextTime = nextTime.plus(1, java.time.temporal.ChronoUnit.SECONDS);
        }

        return nextTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 주어진 시간이 유효한지 확인
     *
     * @param time 시간
     * @return 유효성 여부
     */
    private boolean isValid(LocalDateTime time) {
        return seconds.contains(time.getSecond()) &&
                minutes.contains(time.getMinute()) &&
                hours.contains(time.getHour()) &&
                daysOfMonth.contains(time.getDayOfMonth()) &&
                months.contains(time.getMonthValue()) &&
                daysOfWeek.contains(time.getDayOfWeek().getValue());
    }

    /**
     * 설정된 크론 표현식 문자열 반환
     *
     * @return 크론 표현식 문자열
     */
    public String getCronExp() {
        return String.format("%d %d %d %d %d %d",
                seconds.get(0), minutes.get(0), hours.get(0), daysOfMonth.get(0),
                months.get(0), daysOfWeek.get(0));
    }

    /**
     * 크론 표현식을 기반으로 CronExp 객체 생성
     *
     * @param cronExp 크론 표현식 문자열
     * @return CronExp 객체
     */
    public static CronExp02 create(String cronExp) {
        return new CronExp02(cronExp);
    }
}
