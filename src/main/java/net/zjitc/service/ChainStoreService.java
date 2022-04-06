package net.zjitc.service;

import io.swagger.models.auth.In;
import net.zjitc.entity.ChainStore;

import java.util.List;

public interface ChainStoreService {
    List<ChainStore> findAll();

    ChainStore add(ChainStore chainStore);

    void delete(Integer id);

    ChainStore update(ChainStore chainStore);
}
