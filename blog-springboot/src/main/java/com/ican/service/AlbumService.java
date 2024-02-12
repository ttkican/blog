package com.ican.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Album;
import com.ican.entity.Photo;
import com.ican.enums.FilePathEnum;
import com.ican.mapper.AlbumMapper;
import com.ican.mapper.PhotoMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.AlbumQuery;
import com.ican.model.vo.request.AlbumReq;
import com.ican.model.vo.response.AlbumBackResp;
import com.ican.model.vo.response.AlbumResp;
import com.ican.strategy.context.UploadStrategyContext;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 相册服务
 *
 * @author ican
 */
@Service
public class AlbumService extends ServiceImpl<AlbumMapper, Album> {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private BlogFileService blogFileService;

    public PageResult<AlbumBackResp> listAlbumBackVO(AlbumQuery albumQuery) {
        // 查询相册数量
        Long count = albumMapper.selectCount(new LambdaQueryWrapper<Album>()
                .like(StringUtils.hasText(albumQuery.getKeyword()), Album::getAlbumName, albumQuery.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<AlbumBackResp> albumList = albumMapper.selectBackAlbumList(albumQuery);
        return new PageResult<>(albumList, count);
    }

    public void addAlbum(AlbumReq album) {
        // 相册是否存在
        Album existAlbum = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isNull(existAlbum, album.getAlbumName() + "相册已存在");
        // 添加新相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.insert(newAlbum);
    }

    public void deleteAlbum(Integer albumId) {
        // 查询照片数量
        Long count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        Assert.isFalse(count > 0, "相册下存在照片");
        // 不存在照片则删除
        albumMapper.deleteById(albumId);
    }

    public void updateAlbum(AlbumReq album) {
        // 相册是否存在
        Album existAlbum = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isFalse(Objects.nonNull(existAlbum) && !existAlbum.getId().equals(album.getId()),
                album.getAlbumName() + "相册已存在");
        // 修改相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.updateById(newAlbum);
    }

    public AlbumReq editAlbum(Integer albumId) {
        return albumMapper.selectAlbumById(albumId);
    }

    public List<AlbumResp> listAlbumVO() {
        return albumMapper.selectAlbumVOList();
    }

    public String uploadAlbumCover(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.PHOTO.getFilePath());
        return url;
    }
}




