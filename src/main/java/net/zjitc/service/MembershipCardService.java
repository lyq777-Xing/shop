package net.zjitc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import net.zjitc.entity.MembershipCard;

import java.util.List;

public interface MembershipCardService extends IService<MembershipCard> {
    List<MembershipCard> findAll();

    MembershipCard add(MembershipCard membershipCard);

    MembershipCard findById(Integer id);
}
