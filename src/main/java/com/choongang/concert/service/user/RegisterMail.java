package com.choongang.concert.service.user;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterMail implements MailServiceInter{

    private final JavaMailSender emailsender;

    private String ePw; // 인증번호

    @Override
    public MimeMessage createMessage(String to) throws MessagingException , UnsupportedEncodingException {

        log.info("메일 받을 사용자 : " + to);
        log.info("인증번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO , to);   // 메일 받을 사용자
        message.setSubject("Hot-And-Funny 회원가입 이메일 인증");    // 이메일 인증코드

        String msgg = "";
        msgg += "<div style='margin:100px'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 스프링 백엔드팀 콘서트의 Hot-And-Funny입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>즐거운 시간 보내세요, 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>" + ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("firetrap5319@gmail.com", "HAF_Admin"));// 보내는 사람


        return message;
    }

    @Override
    public String createKey(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String key = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println("생성된 랜덤 인증코드"+ key);

        return key;
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception{

        ePw = createKey();
        log.info("****************생성된 랜덤 인증코드************ => " + ePw);
        MimeMessage message = createMessage(to);
        log.info("****************생성된 랜덤 인증코드************ => " + message);

        try{
            emailsender.send(message);
        }
        catch (MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return ePw; // 메일로 사용자에게 보낸 인증코드를 서버로 반환, 인증코드 일치여부 확인하기.
    }


}
