package com.shoppingwebapp.Controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingwebapp.Dao.MemberRepository;
import com.shoppingwebapp.Model.Member;

@CrossOrigin(allowCredentials = "true", origins = "http://localhost:5173", allowedHeaders = "http://localhost:5173") // set
                                                                                                                     // CORS
@Controller // This means that this class is a Controller
@RequestMapping(path = "/member") // This means URL's start with /demo (after Application path)
public class MemberController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MemberRepository memberRepository;

    @PostMapping(path = "/register") // Create member
    @ResponseBody
    public String createNewUser(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, @RequestParam String phone) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // hash
        String encryptedPassword = passwordEncoder.encode(password);

        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(encryptedPassword);
        member.setPhone(phone);
        member.setAdmin(false);
        memberRepository.save(member);
        return "Success!";
    }

    @PostMapping(path = "/updateMemberInfo") // return member info
    @ResponseBody
    public String updateMemberInfo(@RequestParam String newUsername, @RequestParam String newEmail,
            @RequestParam String password, @RequestParam String newPassword, HttpSession session) {
        Object memberID = session.getAttribute("userId");
        if (memberID != null) {
            Optional<Member> Optional = memberRepository.findById(Integer.parseInt(memberID.toString()));
            Member member = Optional.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, member.getPassword())) {
                // hash
                String encryptedPassword = passwordEncoder.encode(newPassword);
                //set
                member.setUsername(newUsername);
                member.setEmail(newEmail);
                member.setPassword(encryptedPassword);
                memberRepository.save(member);
                return "Success!";
            } else
                return "Fail! Incorrect Password";
        }
        return "Fail!";
    }

    @PostMapping(path = "/requestMemberInfo") // update member info
    @ResponseBody
    public Member requestMemberInfo(HttpSession session) {
        Object memberID = session.getAttribute("userId");
        if (memberID != null) {
            Optional<Member> Optional = memberRepository.findById(Integer.parseInt(memberID.toString()));
            return Optional.get();
        }
        return null;
    }
}
