package com.icia.memberboard.service;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IOException {
        MultipartFile file = memberSaveDTO.getMemberFile();
        String filename = file.getOriginalFilename();
        filename = System.currentTimeMillis() + filename;
        String savepath = "C:\\development\\source\\spring\\springboot\\MemberBoardProject\\src\\main\\resources\\static\\upload\\" + filename;
        if (!file.isEmpty()) {
            file.transferTo(new File(savepath));
        }
        memberSaveDTO.setMemberFilename(filename);
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if(memberEntity != null) {
            if(memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for (MemberEntity m: memberEntityList) {
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
        }
        return memberList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }


    @Override
    public Long update(MemberUpdateDTO memberUpdateDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberUpdateDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public MemberDetailDTO mypage(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> memberEntity = Optional.ofNullable(mr.findByMemberEmail(memberEmail));
        if (memberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}



