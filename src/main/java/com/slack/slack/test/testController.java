package com.slack.slack.test;

import com.slack.slack.appConfig.prometheus.Prometheus;
import com.slack.slack.error.exception.ErrorCode;
import com.slack.slack.error.exception.ResourceNotFoundException;
import com.slack.slack.mail.MailDTO;
import com.slack.slack.mail.MailForm;
import com.slack.slack.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class testController {

    @Value("${spring.mail.toAddress}")
    private String toAddress;

    @Autowired
    private MailService mailService;

    @GetMapping("/csrf/{token}")
    public String csrf_mail_get(@PathVariable String token) {
        Map<String, String> model = new HashMap<>();
        model.put("token", token);

        MailForm mailForm = MailForm.builder()
                .templatePath("csrf.mustache")
                .htmlText(true)
                .model(model)
                .subject("csrf 악성 메일 입니다.")
                .toAddress(toAddress)
                .build();

        mailService.mailSend(mailForm);

        return "complete";
    }

    @GetMapping("/csrf")
    public String none_csrf_mail_get() {

        MailForm mailForm = MailForm.builder()
                .templatePath("none_csrf.mustache")
                .htmlText(true)
                .subject("csrf 악성 메일 입니다.")
                .toAddress(toAddress)
                .build();

        mailService.mailSend(mailForm);

        return "complete";
    }

    /* 프로메테우스 예제 */
//    private final Prometheus prometheus;
//
//    @GetMapping("/test")
//    public void test() {
//        prometheus.getCounter().increment();
//    }
//
//    @GetMapping("exception_test")
//    public String test_get() {
//        if (true) {
//            throw new ResourceNotFoundException(ErrorCode.INVALID_INPUT_VALUE);
//        }
//        return "GetMapping";
//    }
//
//    @PutMapping("test")
//    public String test_put() {
//        return "PutMapping";
//    }
//
//    @DeleteMapping("test")
//    public String test_delete() {
//        return "DeleteMapping";
//    }

}
