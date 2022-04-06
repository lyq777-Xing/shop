package net.zjitc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.zjitc.entity.MembershipCard;

import java.util.List;

public interface MembershipCardService extends IService<MembershipCard> {
    List<MembershipCard> findAll();

    MembershipCard add(MembershipCard membershipCard);
}
