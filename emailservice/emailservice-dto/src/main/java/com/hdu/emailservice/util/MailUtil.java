package com.hdu.emailservice.util;

import com.hdu.emailservice.dto.Inbox;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送工具类
 * sixl
 */
public class MailUtil {
    public static void sendMail( String hosts,String protocol,String senderAddress,
                                 List<String> recipientAddresses,List<String> copyAddresses,
                                 String senderAccount,String senderPassword,String subject,String content) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", protocol);
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", hosts);
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,senderAddress,recipientAddresses,copyAddresses,subject,content);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());

        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

        //5、关闭邮件连接
        transport.close();

    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session, String senderAddress, List<String> recipientAddresses,
                                             List<String> copyAddresses,String subject,String content) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        List<Address> recaddresses=new ArrayList<>();
        List<Address> copaddresses=new ArrayList<>();
        for (String recipientAddress : recipientAddresses) {
            InternetAddress internetAddress = new InternetAddress(recipientAddress);
            recaddresses.add(internetAddress);
        }
        for (String copyAddress : copyAddresses) {
            InternetAddress internetAddress = new InternetAddress(copyAddress);
            copaddresses.add(internetAddress);
        }
        if (recaddresses.size()!=0){
            Address[] addressesrecipient = new Address[recaddresses.size()];
            msg.setRecipients(MimeMessage.RecipientType.TO,recaddresses.toArray(addressesrecipient));
        }
        if (copaddresses.size()!=0){
            Address[] addressescopy = new Address[recaddresses.size()];
            msg.setRecipients(MimeMessage.RecipientType.CC,copaddresses.toArray(addressescopy));
        }
        //设置邮件主题
        msg.setSubject(subject,"UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
