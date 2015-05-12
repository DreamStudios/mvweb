package com.blueshit.neweast.mvpicture.service.impl;

import com.blueshit.neweast.mvpicture.entity.Picture;
import com.blueshit.neweast.mvpicture.service.PictureService;
import com.blueshit.neweast.repository.PictureRepository;
import com.blueshit.neweast.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片管理接口实现类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:38
 * @description
 */
@Service
public class PictureServiceImpl implements PictureService {
    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    private PictureRepository pictureRepository;
    @Autowired
    public  HttpSession       httpSession;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    /**
     * 分页查询图片信息
     * @param pageNo 当前页
     * @param style 图片形式(1:自有，2:第三方)
     * @param ptype 图片分类
     * @param status 运行状态(1开启，0暂停)
     * @return
     */
    @Override
    public Page<Picture> getPicturePage(int pageNo, int style, int ptype, int status) {
        StringBuffer hql = new StringBuffer("From Picture WHERE 1=1 ");
        if (0 != style) {
            hql.append(" AND style=" + style);
        }
        if (0 != ptype) {
            hql.append(" AND ptype=" + ptype);
        }
        if (-1 != status) {
            hql.append(" AND status=" + status);
        }
        hql.append(" ORDER BY status DESC,weight ASC,updateTime DESC"); //排序
        return pictureRepository.findByHql(hql.toString(), Constant.PAGE_SIZE, pageNo);
    }

    /**
     * 修改图片投放状态
     * @param ids 图片ID列表
     * @param status 投放状态
     * @return
     */
    public boolean updatePictureStatus(String ids,int status){
        if(null != ids && 0 < ids.length()){
            try{
                pictureRepository.updateByHql("UPDATE Picture set status="+status+" WHERE id IN ("+ids+")");
            }catch(Exception ex){
                return false;
            }
        }
        return true;
    }

    /**
     * 修改图片权重信息
     * @param id 广告ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param weight 权重
     * @param hour 投放时间段
     * @return
     */
    public boolean updatePictureWeight(int id,String startTime,String endTime,int weight,String hour){
        if(null != hour && 0 < hour.length()){
            try{
                pictureRepository.updateByHql("UPDATE Picture set startTime='"+startTime+"',endTime='"+endTime+"',weight="+weight+",hour='"+hour+"' WHERE id="+id);
            }catch(Exception ex){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据ID获取图片实体信息
     * @param id 图片ID
     * @return
     */
    public Picture getPictureById(int id){
        return pictureRepository.findOne(id);
    }

    /**
     * 添加图片
     * @param picture 图片对象
     * @return
     */
    public boolean addPicture(Picture picture){
        try {
            picture.setStatus(0);
            List<Picture> pictures = new ArrayList<Picture>();
            if (1 == picture.getStyle()) {//自有
                Object obj = httpSession.getAttribute("pictures");
                if (null != obj) {
                    List<String> list = (List<String>) obj;
                    for (String url : list) {
                        Picture temp = (Picture) picture.clone();
                        temp.setUrl(url);
                        pictures.add(temp);
                    }
                }
            } else {//第三方
                String urls = picture.getUrl();
                for(String url : urls.split(",")){
                    Picture temp = (Picture) picture.clone();
                    temp.setUrl(url);
                    pictures.add(temp);
                }
            }
            pictureRepository.save(pictures);
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}
