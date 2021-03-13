package pers.hyu.tyche.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.tyche.dao.BgmMapper;
import pers.hyu.tyche.pojo.entity.Bgm;
import pers.hyu.tyche.pojo.entity.BgmExample;
import pers.hyu.tyche.service.BgmService;

import java.util.List;

/**
 * @see BgmService
 *
 * @author Heng Yu
 * @version 1.0
 */
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;


    @Transactional(readOnly = true)
    @Override
    public List<Bgm> getAllBgm() {
        BgmExample bgmExample = new BgmExample();
        bgmExample.setOrderByClause("id asc");
        return bgmMapper.selectByExample(bgmExample);
    }

    @Transactional(readOnly = true)
    @Override
    public Bgm getById(String id) {
        return bgmMapper.selectByPrimaryKey(id);
    }
}
