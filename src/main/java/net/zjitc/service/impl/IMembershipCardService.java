package net.zjitc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.zjitc.entity.MembershipCard;
import net.zjitc.mapper.MembershipCardMapper;
import net.zjitc.service.MembershipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IMembershipCardService extends ServiceImpl<MembershipCardMapper,MembershipCard> implements MembershipCardService {

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    /**
     * 查询所有会员卡信息（无分页）
     * @return
     */
    @Override
    public List<MembershipCard> findAll() {
        List<MembershipCard> membershipCards = membershipCardMapper.selectList(null);
        return membershipCards;
    }

    /**
     * 添加会员卡信息
     */
    @Override
    public MembershipCard add(MembershipCard membershipCard) {
        membershipCardMapper.insert(membershipCard);
        return membershipCard;
    }
}
