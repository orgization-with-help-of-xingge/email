package com.hdu.emailservice.common.util;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.Recipients;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void getSubject(Inbox email) throws MessagingException {
        StringBuffer content = new StringBuffer(300);
        byte[] mailInfo = email.getMessageBody();
        InputStream is = new ByteArrayInputStream(mailInfo);
        Message message = new MimeMessage(null, is);
        MimeMessage msg = (MimeMessage) message;
        email.setSubject(msg.getSubject());
    }

    public String getContent(Inbox email) throws Exception {
        StringBuffer content = new StringBuffer(300);
        byte[] mailInfo = email.getMessageBody();
        InputStream is = new ByteArrayInputStream(mailInfo);
        Message message = new MimeMessage(null, is);
        MimeMessage msg = (MimeMessage) message;
        //设置邮件id
        String messageID = msg.getMessageID();
        email.setMessageName(messageID);
        //接收者
        List<Recipients> recipients = new ArrayList<>();
        Address[] addresses = msg.getRecipients(Message.RecipientType.TO);
        for (Address address : addresses) {
            Recipients recipient = new Recipients();
            recipient.setRecipients(getRealFromAddress(address.toString()));
            recipients.add(recipient);
        }
        email.setRecipients(recipients);

        //抄送
        List<Recipients> copys = new ArrayList<>();
        Address[] copyAddresses = msg.getRecipients(Message.RecipientType.CC);
        if (copyAddresses != null && copyAddresses.length > 0){
            for (Address copyAddress : copyAddresses) {
                Recipients copy = new Recipients();
                copy.setRecipients(getRealFromAddress(copyAddress.toString()));
                copys.add(copy);
            }
        }
        email.setCopys(copys);

        int size = msg.getSize();
        email.setSize(size);
        email.setSubject(msg.getSubject());
        getMailTextContent(msg, content);
        email.setContent(content.toString());
        return content.toString();
    }

    //根据address，得到真实邮件地址
    public String getRealFromAddress(String address){
        if (address.contains("<")||address.contains(">")){
            return address.split("<")[1].split(">")[0];
        } else {
            return address;
        }
    }

}
