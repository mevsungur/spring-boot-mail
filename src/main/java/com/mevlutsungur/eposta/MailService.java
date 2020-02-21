package com.mevlutsungur.eposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Service
public class MailService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String from;


    @Autowired
    SpringTemplateEngine templateEngine;

    @Async
    public void htmlEpostaGonder(String kime, String konu) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        // Context Tymeleaf'ten import edilmelidir.
        Context context = new Context();
        context.setVariable("metin", "Bu metin Thymeleaf ile oluşturulmuştur. ");
        context.setVariable("list", Arrays.asList("Thymeleaf", "Liste", "İçinde", "Dönebilir"));
        String icerik = templateEngine.process("MerhabaDunya.html", context);

        try {
            helper.setFrom(from);
            helper.setTo(kime);
            helper.setSubject(konu);
            helper.setText(icerik,true);
            //Epostaya ek yüklemek istiyorsanız aşağıdaki alandan dosya eklemesi yapabilirsiniz.
            //helper.addAttachment("name", new File(""));
        } catch (MessagingException e) {
            log.error("E-posta gönderimi yapılırken hata oluştu. " + e.getMessage());
            e.printStackTrace();
        }

        mailSender.send(message);
        log.info(kime + " adresine başarılı e-posta gönderimi yapıldı.");

    }


    @Async
    public void epostaGonder(String kime, String konu, String icerik) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(from);
            helper.setTo(kime);
            helper.setSubject(konu);
            helper.setText(icerik);
            //Epostaya ek yüklemek istiyorsanız aşağıdaki alandan dosya eklemesi yapabilirsiniz.
            //helper.addAttachment("name", new File(""));
        } catch (MessagingException e) {
            log.error("E-posta gönderimi yapılırken hata oluştu. " + e.getMessage());
            e.printStackTrace();
        }

        mailSender.send(message);
        log.info(kime + " adresine başarılı e-posta gönderimi yapıldı.");

    }



}
