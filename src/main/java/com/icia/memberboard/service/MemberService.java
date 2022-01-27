package com.icia.memberboard.service;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IOException;

    boolean login(MemberLoginDTO memberLoginDTO);

    List<MemberDetailDTO> findAll();

    MemberDetailDTO findById(Long memberId);

    void deleteById(Long memberId);

    Long update(MemberUpdateDTO memberUpdateDTO);

    MemberDetailDTO mypage(String memberEmail);

    boolean emailCheck(String memberEmail);
}

