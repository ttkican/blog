package com.ican.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Carousel;
import com.ican.enums.FilePathEnum;
import com.ican.mapper.CarouselMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.CarouselQuery;
import com.ican.model.vo.request.CarouselReqVo;
import com.ican.model.vo.request.CarouselStatusReq;
import com.ican.model.vo.response.CarouselBackResp;
import com.ican.model.vo.response.CarouselResp;
import com.ican.strategy.context.UploadStrategyContext;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 轮播图服务
 *
 * @author ican
 */
@Service
public class CarouselService extends ServiceImpl<CarouselMapper, Carousel> {

    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private BlogFileService blogFileService;

    public PageResult<CarouselBackResp> getCarouselVOList(CarouselQuery carouselQuery) {
        // 查询轮播图数量
        Long count = carouselMapper.selectCount(null);
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询轮播图列表
        List<CarouselBackResp> carouselList = carouselMapper.selectBackCarouselList(carouselQuery);
        return new PageResult<>(carouselList, count);
    }

    public void addCarousel(CarouselReqVo carouselReqVo) {
        Carousel carousel = BeanCopyUtils.copyBean(carouselReqVo, Carousel.class);
        carouselMapper.insert(carousel);
    }

    public void updateCarousel(CarouselReqVo carouselReqVo) {
        Assert.notNull(carouselReqVo.getId(), "id is null");
        Carousel carousel = BeanCopyUtils.copyBean(carouselReqVo, Carousel.class);
        carouselMapper.updateById(carousel);
    }

    public List<CarouselResp> getCarouselList() {
        return carouselMapper.selectCarouselList();
    }

    public String uploadCarousel(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CAROUSEL.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.CAROUSEL.getFilePath());
        return url;
    }

    public void updateCarouselStatus(CarouselStatusReq carouselStatusReq) {
        Carousel carousel = Carousel.builder()
                .id(carouselStatusReq.getId())
                .status(carouselStatusReq.getStatus())
                .build();
        carouselMapper.updateById(carousel);
    }

}
