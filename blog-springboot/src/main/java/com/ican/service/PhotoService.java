package com.ican.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Album;
import com.ican.entity.Photo;
import com.ican.enums.FilePathEnum;
import com.ican.mapper.AlbumMapper;
import com.ican.mapper.PhotoMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.PhotoQuery;
import com.ican.model.vo.request.PhotoInfoReq;
import com.ican.model.vo.request.PhotoReq;
import com.ican.model.vo.response.AlbumBackResp;
import com.ican.model.vo.response.PhotoBackResp;
import com.ican.model.vo.response.PhotoResp;
import com.ican.strategy.context.UploadStrategyContext;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 照片业务接口实现类
 *
 * @author ican
 */
@Service
public class PhotoService extends ServiceImpl<PhotoMapper, Photo> {

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private BlogFileService blogFileService;

    public PageResult<PhotoBackResp> listPhotoBackVO(PhotoQuery photoQuery) {
        // 查询照片数量
        Long count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(photoQuery.getAlbumId()), Photo::getAlbumId, photoQuery.getAlbumId()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询照片列表
        List<PhotoBackResp> photoList = photoMapper.selectBackPhotoList(photoQuery);
        return new PageResult<>(photoList, count);
    }

    public AlbumBackResp getAlbumInfo(Integer albumId) {
        AlbumBackResp albumBackResp = albumMapper.selectAlbumInfoById(albumId);
        if (Objects.isNull(albumBackResp)) {
            return null;
        }
        Long photoCount = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        albumBackResp.setPhotoCount(photoCount);
        return albumBackResp;
    }

    public void addPhoto(PhotoReq photo) {
        // 批量保存照片
        List<Photo> pictureList = photo.getPhotoUrlList().stream()
                .map(url -> Photo.builder()
                        .albumId(photo.getAlbumId())
                        .photoName(IdWorker.getIdStr())
                        .photoUrl(url)
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(pictureList);
    }

    public void updatePhoto(PhotoInfoReq photoInfo) {
        Photo photo = BeanCopyUtils.copyBean(photoInfo, Photo.class);
        baseMapper.updateById(photo);
    }

    public void deletePhoto(List<Integer> photoIdList) {
        baseMapper.deleteBatchIds(photoIdList);
    }

    public void movePhoto(PhotoReq photo) {
        List<Photo> photoList = photo.getPhotoIdList().stream()
                .map(photoId -> Photo.builder()
                        .id(photoId)
                        .albumId(photo.getAlbumId())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
    }

    public Map<String, Object> listPhotoVO(Integer albumId) {
        Map<String, Object> result = new HashMap<>(2);
        String albumName = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                        .select(Album::getAlbumName).eq(Album::getId, albumId))
                .getAlbumName();
        List<PhotoResp> photoVOList = photoMapper.selectPhotoVOList(albumId);
        result.put("albumName", albumName);
        result.put("photoVOList", photoVOList);
        return result;
    }

    public String uploadPhoto(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.PHOTO.getFilePath());
        return url;
    }
}