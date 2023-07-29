package com.sky.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Slf4j
public class GlobalTimeInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int hour = 6;
        int minute = 0;
        if (LocalTime.now().isAfter(LocalTime.of(4, minute))) {
            log.info("現在の時刻は{}、許可です", LocalDateTime.now());

            return true;
        } else {
            log.info("現在の時刻は{}、許可されていません", LocalDateTime.now());
            response.sendError(401);
            return false;
        }
    }


}
