package com.icia.memberboard.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;
import com.icia.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.icia.memberboard.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;

    //회원가입 폼
    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("member", new MemberSaveDTO());
        return "member/save";
    }

    //회원가입 처리
    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "member/save";
        }
        try {
            Long memberId = ms.save(memberSaveDTO);
        } catch (IllegalStateException e) {
            bindingResult.reject("emailCheck", e.getMessage());
            return "member/save";
        }
        return "redirect:/member/login";
    }

    // 로그인 창
    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginDTO());
        return "member/login";
    }

    // 로그인처리
    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }
        boolean loginResult = ms.login(memberLoginDTO);
        if (ms.login(memberLoginDTO)) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            if (memberLoginDTO.getMemberEmail().equals("admin")) {
                return "member/admin";
            }
            return "redirect:/board";
        } else {
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다.");
            return "member/login";
        }
    }

    // 회원목록(findAll)
    @GetMapping("findAll")
    public String findAll(Model model) {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";
    }

    // 회원 상세조회(findById_Get)
    @GetMapping("{memberId}")
    public String findById(@PathVariable Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/findById";
    }

    //회원삭제(member/delete/5)_Get
    @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        return "redirect:/member/findAll";
    }

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("mypage")
    public String mypage(HttpSession session, Model model) {
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO member = ms.mypage(memberEmail);
        model.addAttribute("member", member);
        return "member/mypage";
    }

    @GetMapping("update/{memberId}")
    public String updateForm(@PathVariable Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberUpdateDTO memberUpdateDTO) {
        Long memberId = ms.update(memberUpdateDTO);
        return "redirect:/member/" + memberUpdateDTO.getMemberId();
    }

    @GetMapping("admin")
    public String admin() {
        return "member/admin";
    }

    //  Email 중복체크
    @PostMapping("emailCheck")
    @ResponseBody
    public String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        if (ms.emailCheck(memberEmail)) {
            return "ok";
        } else {
            return "no";
        }
    }

}