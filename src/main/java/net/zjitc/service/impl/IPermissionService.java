package net.zjitc.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Permission;
import net.zjitc.entity.vo.PermissionVo;
import net.zjitc.mapper.PermissionMapper;
import net.zjitc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class IPermissionService implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionVo> findPermissionList() {
        List<PermissionVo> permissionList = permissionMapper.findPermissionList();
        return permissionList;
    }

    @Override
    public List<PermissionVo> findPermissionTree() {
        List<PermissionVo> onelist = permissionMapper.findPermissionTree(1);
        List<PermissionVo> twolist = permissionMapper.findPermissionTree(3);
        List<PermissionVo> threelist = permissionMapper.findPermissionTree(2);
            //        将第三层放在第二层下面
            for (PermissionVo two : twolist) {
                ArrayList<PermissionVo> list = new ArrayList<>();
                for (PermissionVo three : threelist) {
                    if (three.getPid().intValue() == two.getId().intValue()) {
                        list.add(three);
                    }
                }
                two.setChildren(list);
            }
            //        将第二层放在第一层下面
            for (PermissionVo one : onelist) {
                ArrayList<PermissionVo> list1 = new ArrayList<>();
                for (PermissionVo two1 : twolist) {
                    if (two1.getPid().intValue() == one.getId().intValue()) {
                        list1.add(two1);
                    }
                }
                one.setChildren(list1);
            }
        return onelist;
    }

    @Override
    public List<PermissionVo> findMenus() {
        List<PermissionVo> onelist = permissionMapper.findPermissionTree(1);
        List<PermissionVo> twolist = permissionMapper.findPermissionTree(3);
        //        将第二层放在第一层下面
        for (PermissionVo one : onelist) {
            ArrayList<PermissionVo> list1 = new ArrayList<>();
            for (PermissionVo two1 : twolist) {
                if (two1.getPid().intValue() == one.getId().intValue()) {
                    list1.add(two1);
                }
            }
            one.setChildren(list1);
        }
        return onelist;
    }

    @Override
    public List<PermissionVo> findByRolePid(List<String> list) {
//        查询层级一
//        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
//        wrapper.in("ps_id",list).eq("ps_level",1);
        List<PermissionVo> list1 = permissionMapper.findPermissionTree(1);
        List<PermissionVo> onee=new ArrayList<>();
        for (PermissionVo oneee:list1) {

            for(int i=0;i<list.size();i++){

                if(String.valueOf(oneee.getId()).equals(list.get(i))){
                    onee.add(oneee);

                }

            }


        }

//        for (Permission ll :list1) {
//            if(ll.)
//
//        }
//        查询层级二
//        QueryWrapper<Permission> wrapper1 = new QueryWrapper<>();
//        wrapper1.in("ps_id",list).eq("ps_level",3);
//        List<Permission> list2 = permissionMapper.selectList(wrapper1);
        List<PermissionVo> list2 = permissionMapper.findPermissionTree(3);
        List<PermissionVo> twoo=new ArrayList<>();
        for (PermissionVo twooo:list2) {

            for(int i=0;i<list.size();i++){

                if(String.valueOf(twooo.getId()).equals(list.get(i))){
                    twoo.add(twooo);

                }

            }


        }
//        查询层级三
//        QueryWrapper<Permission> wrapper2 = new QueryWrapper<>();
//        wrapper2.in("ps_id",list).eq("ps_level",2);
//        List<Permission> list3 = permissionMapper.selectList(wrapper2);
        List<PermissionVo> list3 = permissionMapper.findPermissionTree(2);
        List<PermissionVo> threee=new ArrayList<>();
        for (PermissionVo threeee:list3) {

            for(int i=0;i<list.size();i++){

                if(String.valueOf(threeee.getId()).equals(list.get(i))){
                    threee.add(threeee);

                }

            }


        }



        for (PermissionVo two:twoo) {
            List<PermissionVo> arrayList = new ArrayList<>();
            for (PermissionVo three:threee) {
                if(two.getId().intValue()==three.getPid().intValue()){
                    three.setLevel(null);
//                    three.setPid(null);
                    arrayList.add(three);
                }
            }
            two.setChildren(arrayList);
        }




        for (PermissionVo one:onee) {
            List<PermissionVo> arrayList = new ArrayList<>();
            for (PermissionVo two:twoo) {

                if(one.getId().intValue()==two.getPid().intValue()){
//                    two.setPid(null);
                    two.setLevel(null);
                    arrayList.add(two);
                }

            }
            one.setChildren(arrayList);

        }
        for (PermissionVo ll :onee) {
//            ll.setPid(null);
            ll.setLevel(null);

        }
        if(onee == null || onee.size() == 0){
            if(twoo == null || twoo.size() == 0){
                return threee;
            }else {
                return twoo;
            }
        }else {
            return onee;
        }
    }


}
