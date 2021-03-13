package pers.hyu.tyche.service;

import pers.hyu.tyche.pojo.entity.Bgm;

import java.util.List;

/**
 * The interface is the CRUD operations with background music.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface BgmService {
    /**
     * Get all the background music.
     *
     * @return The list of all the background
     */
    List<Bgm> getAllBgm();

    /**
     * Get the background music by the id.
     *
     * @param id the id of the bgm.
     * @return The bgm entity.
     */
    Bgm getById(String id);

}
