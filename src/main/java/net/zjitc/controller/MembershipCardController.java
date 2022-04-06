package net.zjitc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.MembershipCard;
import net.zjitc.service.MembershipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "会员卡相关接口", description = "提供会员卡相关的Rest API")
public class MembershipCardController {
    @Autowired
    private MembershipCardService membershipCardService;

    /**
     * 查询所有会员卡信息 不带分页
     * @return
     */
    @ApiOperation(value = "查询所有会员卡信息(不带分页)")
    @PreAuthorize("hasAuthority('getAllMembershipCard')")
    @GetMapping("/membershipcard/list")
    public ResponseResult findAll(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<MembershipCard> all = membershipCardService.findAll();
            result.Success("查询成功",all);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }


    /**
     * 根据id修改会员卡信息
     * @param membershipCard
     * @return
     */
    @ApiOperation(value = "根据id修改会员卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MembershipCard", value = "会员卡对象", required = true)
    })
    @PreAuthorize("hasAuthority('updateMembershipCard')")
    @PostMapping("/membershipcard/update")
    public ResponseResult updete(@RequestBody MembershipCard membershipCard){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            boolean b = membershipCardService.updateById(membershipCard);
            if(b){
                result.Success("修改成功",membershipCard);
            }else {
                result.BadRequest("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("修改失败");
        }
        return result;
    }

    /**
     * 根据id删除会员卡信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除会员卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteMembershipCard')")
    @DeleteMapping("/membershipcard/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            boolean b = membershipCardService.removeById(id);
            if(b){
                result.Success("删除成功");
            }else {
                result.BadRequest("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("删除失败");
        }
        return result;
    }

    /**
     * 添加会员卡信息
     * @param membershipCard
     * @return
     */
    @ApiOperation(value = "添加会员卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MembershipCard", value = "会员卡对象", required = true)
    })
    @PreAuthorize("hasAuthority('addMembershipCard')")
    @PostMapping("/membershipcard/add")
    public ResponseResult add(@RequestBody MembershipCard membershipCard){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            MembershipCard add = membershipCardService.add(membershipCard);
            if(add == null){
                result.BadRequest("添加错误");
            }else {
                result.Success("添加成功",add);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("添加错误");
        }
        return result;
    }
}
