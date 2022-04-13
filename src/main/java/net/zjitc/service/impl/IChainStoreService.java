package net.zjitc.service.impl;

import net.zjitc.entity.ChainStore;
import net.zjitc.mapper.ChainStoreMapper;
import net.zjitc.service.ChainStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IChainStoreService implements ChainStoreService {
    @Autowired
    private ChainStoreMapper chainStoreMapper;

    /**
     * 查询所有连锁店信息（不带分页）
     * @return
     */
    @Override
    public List<ChainStore> findAll() {
        List<ChainStore> chainStores = chainStoreMapper.selectList(null);
        return chainStores;
    }

    /**
     * 添加连锁店信息
     * @param chainStore
     * @return
     */
    @Override
    public ChainStore add(ChainStore chainStore) {
        chainStoreMapper.insert(chainStore);
        return chainStore;
    }

    /**
     * 根据id删除连锁店信息
     * @param id
     */
    @Override
    public void delete(Integer id) {
        chainStoreMapper.deleteById(id);
    }

    /**
     * 更新连锁店信息
     * @param chainStore
     * @return
     */
    @Override
    public ChainStore update(ChainStore chainStore) {
        chainStoreMapper.updateById(chainStore);
        return chainStore;
    }

    @Override
    public ChainStore findById(Integer id) {
        ChainStore chainStore = chainStoreMapper.selectById(id);
        return chainStore;
    }
}
