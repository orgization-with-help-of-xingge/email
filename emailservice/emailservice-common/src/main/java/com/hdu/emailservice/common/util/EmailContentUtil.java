package com.hdu.emailservice.common.util;
import com.hdu.emailservice.dto.Inbox;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 得到邮件内容，发件收件人的工具类
 * @Author: sixl
 * @Date: 2019/11/12 14:00
 */
@Slf4j
public class EmailContentUtil {

    //获得邮箱的正文
    public void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        System.out.println("邮件的内容：——————" + part.getContentType());
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    //获取发发送的时间
    public String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null)
            return "";
        if (pattern == null || "".equals(pattern))
            pattern = "yyyy年MM月dd日 E HH:mm ";

        return new SimpleDateFormat(pattern).format(receivedDate);
    }

    //获得发件人的信息
    public String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from;
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    public String getContent(Inbox email) throws Exception {
        StringBuffer content = new StringBuffer(300);
        byte[] mailInfo = email.getMessageBody();
        InputStream is = new ByteArrayInputStream(mailInfo);
        Message message = new MimeMessage(null, is);
        MimeMessage msg = (MimeMessage) message;
        int size = msg.getSize();
        email.setSize(size);
        email.setSubject(msg.getSubject());
//        log.info("邮件的大小为———>{}", size);
//        log.info("邮件接收时间———>{}", getSentDate(msg, null));
//        log.info("发件人地址———>{}", getFrom(msg));
        getMailTextContent(msg, content);
        email.setContent(content.toString());
//        log.info("邮件的正文———>{}", content);

        log.info("邮件解析完成");
        return content.toString();
    }

}
