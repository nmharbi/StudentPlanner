package com.example.studentplanner.service;

import com.example.studentplanner.models.User;
import com.example.studentplanner.repositorys.UserRepo;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JavaMailSender mailSender;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    
    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User get(Long id){
            return userRepo.findById(id).orElseThrow(()->new IllegalStateException("user does not exist!"));
    }

    public User getByEmail(String email){
            return userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("There are no user registered with this email!"));
    }

    public User add(User user,String URL) throws UnsupportedEncodingException, MessagingException {
        user.setVerificationCode(RandomString.make(64));
        user.setEnabled(false);
        user.setAdmin(false);
        User u = userRepo.save(user);
        sendVerificationEmail(user,URL);
        return u;
    }

    public void delete(Long id){
        userRepo.deleteById(id);
    }

    public boolean checkIfExists(String email){
        return userRepo.findByEmail(email).isEmpty();
    }

    public User login(String email , String password){
        if(userRepo.findByEmail(email).isPresent()){
            User s = userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("user does not exist!"));
            if(s.getPassword().equals(password))
                return s;
        }
        return null;
    }

    @Transactional
    public User update(User user, Long id){
        User user1 = get(id);
        if(user.getPassword() != null && user.getPassword().length()>5)
            user1.setPassword(user.getPassword());
        if(user.getName() != null && user.getName().length()>0)
            user1.setName(user.getName());
        if(user.getDepartment() != null && user.getDepartment().length()>0)
            user1.setDepartment(user.getDepartment());
        if(user.getEmail() != null && user.getEmail().length() > 0 )
            if(checkIfExists(user.getEmail()))
                user1.setEmail(user.getEmail());
            else
                throw new IllegalStateException("email already exists exist");
        return user1;
    }

    @Transactional
    public void resetPassword(String id,String password){
        User user = userRepo.findByVerificationCode(id);
        user.setPassword(password);
        user.setVerificationCode(null);
    }

    @Transactional
    public void blockUser(Long id){
        User student = get(id);
        if(student != null )
            student.setCommentCertified(false);
        reviewService.deleteAllStudentComments(student.getId());
    }

    @Transactional
    public void unblockUser(Long id){
        User student = get(id);
        if(student != null )
            student.setCommentCertified(true);
    }

    public List<User> getBlockedUsers(){
        return userRepo.findByCommentCertifiedFalse();
    }

    private void sendVerificationEmail(User user,String URL) throws MessagingException, UnsupportedEncodingException {
            String toAddress = user.getEmail();
            String fromAddress = "plannerstudent77@gmail.com";
            String senderName = "Student planner application";
            String subject = "Please verify your registration at Student planner application";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "Student planner application.";
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            content = content.replace("[[name]]", user.getName());
            String verifyURL = URL+"verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", verifyURL);
            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("Email has been sent");
        }

    @Transactional
    public void sendResetPasswordEmail(User user,String URL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "plannerstudent77@gmail.com";
        String senderName = "Student planner application";
        String subject = "Reset password email ";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">reset password</a></h3>"
                + "Thank you,<br>"
                + "Student planner application.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getName());
        user.setVerificationCode(RandomString.make(64));
        String verifyURL = URL;
        verifyURL+="/reset?url="+URL+"token="+user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Email has been sent");
    }



    @Transactional
    public Boolean verify(String verificationCode){
        User user = userRepo.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
        return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }
    }

}
