package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.RepoService.UserRepoService;
import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.Request.EmailInfo;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;
import binson.banking.binsonbank.otp.OtpSystem;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


@Service
@PropertySource(value = {"classpath:application.properties"})
public class UserServiceImpl implements UserService {
    private final Map<String, OtpSystem> otp_data = new HashMap<>();
    private final static String ACCOUNT_ID = "AC4bfed91273c38914251e85574285af3e";
    private final static String AUTH_ID = "9169ed2b60a5226393c4c25055de32ef";
        
    static {
        Twilio.init(ACCOUNT_ID, AUTH_ID);
    }

    @Value("${gmail.usename}")
    private String emailId;
    @Value("${gmail.password}")
    private String password;

    @Autowired
    private UserRepoService userRepoService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl() {
    }

    @Override
    public ResponseMessage userDataSave(CreateUserRequest createUserRequest) {
        ResponseMessage responseMessage = new ResponseMessage();
        String customer_id = UUID.randomUUID().toString();
        String token = jwtTokenUtil.generateToken(createUserRequest);


        int rowCount = userRepoService.userDataSave(createUserRequest, token, customer_id);

        if (rowCount < 1) {
            responseMessage.setResponseMessage("something went wrong");

        } else {
            responseMessage.setToken(token);
            responseMessage.setResponseMessage("successfully updated");
        }


        return responseMessage;
    }

    @Override
    public UserDetailsAggregate getUserDetails(int accountNumber) {
        String token = httpServletRequest.getHeader("Authorization");
        UserDetailsAggregate userDetailsAggregate = userRepoService.getUserDetails(accountNumber);
        if (jwtTokenUtil.validateToken(token, userDetailsAggregate)) {
            return userDetailsAggregate;
        } else {
            return null;
        }

    }

    @Override
    public ResponseMessage changePswd(CreateUserRequest createUserRequest) {
        int accountNumber = createUserRequest.getAccountNumber();
        String password = createUserRequest.getPassword();
        ResponseMessage responseMessage = new ResponseMessage();
        int rowCount = userRepoService.changePswd(accountNumber, password);
        if (rowCount < 1) {


            responseMessage.setResponseMessage("something went wrong");
        } else {
            responseMessage.setResponseMessage(" password updated successfully");

        }
        return responseMessage;
    }

    @Override
    public ResponseMessage userLogin(CreateUserRequest createUserRequest) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserDetailsAggregate userDetailsAggregate = userRepoService.getUserDetails(createUserRequest.getAccountNumber());
        if (userDetailsAggregate != null) {
            if (userDetailsAggregate.getPassword().equals(createUserRequest.getPassword())) {
                String token = jwtTokenUtil.generateToken(createUserRequest);
                responseMessage.setToken(token);
                responseMessage.setResponseMessage("success");
                getOtp();

            } else {
                responseMessage.setResponseMessage("in correct password");
            }

        } else {
            responseMessage.setResponseMessage("invalid account number");
        }
        return responseMessage;

    }

    @Override
    public ResponseMessage sendEmail(EmailInfo emailInfo) throws IOException, MessagingException {
        ResponseMessage responseMessage = sendMail(emailInfo);
        return responseMessage;
    }


    private void getOtp() {
        OtpSystem otpSystem = new OtpSystem();
        otpSystem.setMobileNumber("+919074267847");
        otpSystem.setOtp(String.valueOf((int) (Math.random() * (10000 - 1000) + 1000)));
        otpSystem.setExpiryTime(System.currentTimeMillis() + 20000);
        otp_data.put("+919074267847", otpSystem);

        Message.creator(new PhoneNumber("+919074267847"), new PhoneNumber("+1 667 206 6727"),
                "your otp is" + otpSystem.getOtp()).create();


    }

    private ResponseMessage sendMail(EmailInfo emailInfo) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("check" + emailId);
                System.out.println("check" + password);
                return new PasswordAuthentication(emailId, password);
            }
        });

        javax.mail.Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailId, false));

        msg.setRecipients(javax.mail.Message.RecipientType.TO
                , InternetAddress.parse(emailInfo.getTo_address()));
        msg.setSubject(emailInfo.getSubject());
        msg.setContent(emailInfo.getBody(), "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailInfo.getBody(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/home/asus/Downloads/index.jpeg");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseMessage("successfully sended");
        return responseMessage;


    }


}
