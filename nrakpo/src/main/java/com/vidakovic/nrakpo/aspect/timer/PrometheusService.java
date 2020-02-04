package com.vidakovic.nrakpo.aspect.timer;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * <p>
 * <b>Title: TimerService  </b>
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
 * @since 04-Feb-20 14:17:42
 */
@Service
public class PrometheusService {

    @Autowired
    PrometheusMeterRegistry meterRegistry;

    public void record(Duration duration, String meterName){
        meterRegistry.timer(meterName).record(duration);
    }


}

