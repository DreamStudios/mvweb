package com.blueshit.neweast.mvpicture.service.impl;

import com.blueshit.neweast.mvpicture.bean.ImageData;
import com.blueshit.neweast.mvpicture.bean.PicturePool;
import com.blueshit.neweast.mvpicture.entity.Picture;
import com.blueshit.neweast.mvpicture.service.PictureService;
import com.blueshit.neweast.repository.PicturePoolRepository;
import com.blueshit.neweast.repository.PictureRepository;
import com.blueshit.neweast.repository.SyncRepository;
import com.blueshit.neweast.utils.Constant;
import com.blueshit.neweast.utils.DateUtil;
import com.blueshit.neweast.utils.DesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    //资源文件地址
    @Value("${resourceFileUrl}")
    private String resourceFileUrl = "http://s3.xlskad.cn/admin";

    private PictureRepository pictureRepository;
    private HttpSession       httpSession;
    private SyncRepository    syncRepository;
    private ObjectMapper      objectMapper;
    private PicturePoolRepository picturePoolRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, HttpSession httpSession,
                              SyncRepository syncRepository, ObjectMapper objectMapper,
                              PicturePoolRepository picturePoolRepository) {
        this.pictureRepository = pictureRepository;
        this.httpSession = httpSession;
        this.syncRepository = syncRepository;
        this.objectMapper = objectMapper;
        this.picturePoolRepository = picturePoolRepository;
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
        hql.append(" AND status=" + status);
        hql.append(" ORDER BY status DESC,ptype ASC,weight ASC,updateTime DESC"); //排序
        return pictureRepository.findByHql(hql.toString(), Constant.PAGE_SIZE, pageNo);
    }

    /**
     * 修改图片投放状态
     * @param ids 图片ID列表
     * @param status 投放状态
     * @return
     */
    @Override
    public boolean updatePictureStatus(String ids,int status){
        if(null != ids && 0 < ids.length()){
            try{
                pictureRepository.updateByHql("UPDATE Picture set status="+status+",sync=1 WHERE id IN ("+ids+")");
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
    @Override
    public boolean updatePictureWeight(int id,String startTime,String endTime,int weight,String hour){
        if(null != hour && 0 < hour.length()){
            try{
                pictureRepository.updateByHql("UPDATE Picture set startTime='"+startTime+"',endTime='"+endTime+"',weight="+weight+",hour='"+hour+"',sync=1 WHERE id="+id);
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
    @Override
    public Picture getPictureById(int id){
        return pictureRepository.findOne(id);
    }

    /**
     * 添加图片
     * @param picture 图片对象
     * @return
     */
    @Override
    public boolean addPicture(Picture picture){
        try {
            picture.setStatus(0);
            picture.setSync(1);
            if(null != picture.getHour() && !picture.getHour().isEmpty()){
                picture.setHour(","+picture.getHour()+",");
            }
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

    /**
     * 同步图片信息
     * @return
     */
    @Override
    public boolean syncPictures(){
        String now = DateUtil.getPreAfterDate(0);
        String hour = "," + DateUtil.getHour(new Date()) + ",";
        StringBuffer hql = new StringBuffer("From Picture WHERE status=1 ");
        hql.append(" AND startTime <='" + now + "'");
        hql.append(" AND endTime >='" + now + "'");
        hql.append(" AND (hour IS NULL OR hour='' OR hour LIKE '%" + hour + "%') ");
        hql.append(" ORDER BY ptype ASC,weight ASC,updateTime DESC"); //排序
        List<Picture> list = pictureRepository.whFindByList(hql.toString());
        try{
            syncPicture(list);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    //同步数据集合
    private void syncPicture(List<Picture> list) throws Exception{
        if(null != list && 0 < list.size()){
            Map<String, String> details = new HashMap<String, String>();
            Map<Integer,List<Integer>> pool = new HashMap<Integer, List<Integer>>();
            for(Picture picture : list){
                if(1 == picture.getStyle()){//自有图片
                    picture.setUrl(resourceFileUrl + picture.getUrl());
                }
                if(1 == picture.getSync()){//是否需要同步
                    ImageData imageData = new ImageData();
                    imageData.setId(picture.getId());
                    imageData.setDownload_url(picture.getUrl());
                    details.put(picture.getId()+"",objectMapper.writeValueAsString(imageData));
                }
                if(pool.containsKey(picture.getPtype())){
                    pool.get(picture.getPtype()).add(picture.getId());
                }else {
                    List<Integer> idList = new ArrayList<Integer>();
                    idList.add(picture.getId());
                    pool.put(picture.getPtype(),idList);
                }
            }
            //将图片详情写入redis
            syncRepository.syncPictureDetail(details);
            //将图片池写入redis
            syncRepository.syncPicturePool(pool);
            //将redis图片池信息加载到内存
            picturePoolRepository.initPicturePool();
        }
    }

    /**
     * 客户端请求图片信息
     * @param key
     * @return
     */
    public String getPictures(String key) throws Exception{
        PicturePool picturePool = new PicturePool();
        //解密出图片类型
        String val = DesUtil.decrypt(key, Constant.DECRYPTKEY);
        List<String> idList = picturePoolRepository.getPicturePool(val);
        if(null != idList) {
            for (String id : idList) {
                picturePool.getData().add(objectMapper.readValue(picturePoolRepository.getPictureById(id),ImageData.class));
            }
        }
        //将反馈信息加密
        return DesUtil.encryptDES(objectMapper.writeValueAsString(picturePool),Constant.DECRYPTKEY);
    }
}
