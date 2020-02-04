package com.vidakovic.nrakpo.aspect.timer;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.Duration;

/**
 * <p>
 * <b>Title: Timer  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2020
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 04-Feb-20
 * @since 04-Feb-20 14:20:42
 */
@Service
public class TimerService {

    PrometheusService prometheusService;

    StopWatch stopWatch;

    public TimerService(PrometheusService prometheusService) {
        this.prometheusService = prometheusService;
    }

    public void start() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    public void stopAndRecord(String meterName) {
        stopWatch.stop();
        prometheusService.record(Duration.ofMillis(stopWatch.getLastTaskTimeMillis()), meterName);
    }


}

