package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.BookDao.MemberDao;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.service.MemberService;
import com.ifortex.bookservice.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

// Attention! It is FORBIDDEN to make any changes in this file!
@Service
public class ESMemberServiceImpl implements MemberService {

    @Override
    public Member findMember() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        MemberDao memberDao = context.getBean(MemberDao.class);
        return memberDao.findMemberByOldestRomanceBookAndRecentRegistration();
    }

    @Override
    public List<Member> findMembers() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        MemberDao memberDao = context.getBean(MemberDao.class);
        return memberDao.findMembersRegisteredIn2023WithNoBooksRead();
    }
}
