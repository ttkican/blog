package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.Talk;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.TalkDTO;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.TalkBackInfoVO;
import com.ican.model.vo.TalkBackVO;
import com.ican.model.vo.TalkVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 说说业务接口
 *
 * @author ican
 */
public interface TalkService extends IService<Talk> {

    /**
     * 查看后台说说列表
     *
     * @param condition 条件
     * @return {@link PageResult<TalkBackVO>} 说说列表
     */
    PageResult<TalkBackVO> listTalkBackVO(ConditionDTO condition);

    /**
     * 添加说说
     *
     * @param talk 说说
     */
    void addTalk(TalkDTO talk);

    /**
     * 删除说说
     *
     * @param talkId 说说id
     */
    void deleteTalk(Integer talkId);

    /**
     * 修改说说
     *
     * @param talk 说说
     */
    void updateTalk(TalkDTO talk);

    /**
     * 编辑说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkBackInfoVO editTalk(Integer talkId);

    /**
     * 查看首页说说
     *
     * @return 首页说说
     */
    List<String> listTalkHome();

    /**
     * 查看说说列表
     *
     * @return 说说列表
     */
    PageResult<TalkVO> listTalkVO();

    /**
     * 查看说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkVO getTalkById(Integer talkId);

    /**
     * 上传说说图片
     *
     * @param file 文件
     * @return 说说图片地址
     */
    String uploadTalkCover(MultipartFile file);
}
