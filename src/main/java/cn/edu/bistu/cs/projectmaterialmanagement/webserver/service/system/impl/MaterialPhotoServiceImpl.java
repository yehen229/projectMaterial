package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhotoView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialPhotoRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialPhotoService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import io.netty.handler.codec.base64.Base64Encoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialPhotoServiceImpl implements IMaterialPhotoService {

    private static final Logger log =
            LoggerFactory.getLogger(MaterialPhotoServiceImpl.class);

    private final IMaterialPhotoRepository materialPhotoRepository;

    private final IMaterialService materialService;

    public MaterialPhotoServiceImpl(IMaterialPhotoRepository materialPhotoRepository, IMaterialService materialService) {
        this.materialPhotoRepository = materialPhotoRepository;
        this.materialService = materialService;
    }

    /**
     * 增加
     */
    @Override
    public String add(MaterialPhoto materialPhoto) {
        return materialPhotoRepository.add(materialPhoto);
    }

    /**
     * 删除
     */
    @Override
    public int delete(MaterialPhoto materialPhoto) {
        return materialPhotoRepository.delete(materialPhoto);
    }

    /**
     * 更新
     */
    @Override
    public int update(MaterialPhoto materialPhoto) {
        return materialPhotoRepository.update(materialPhoto);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialPhotoRepository.deleteById(id);
    }

    /**
     * 根据materialId删除记录
     *
     * @param materialId
     */
    @Override
    public int deleteByMaterialId(String materialId) {
        return materialPhotoRepository.deleteByMaterialId(materialId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialPhotoRepository.getCount();
    }

    /**
     * 根据materialId得到数量
     *
     * @param materialId
     */
    @Override
    public int getCountByMaterialId(String materialId) {
        return materialPhotoRepository.getCountByMaterialId(materialId);
    }

    /**
     * 根据id得到MaterialPhoto
     *
     * @param id
     */
    @Override
    public MaterialPhoto getById(String id) {
        return materialPhotoRepository.getById(id);
    }

    /**
     * 根据materialId得到MaterialPhoto
     *
     * @param materialId
     */
    @Override
    public List<MaterialPhoto> getByMaterialId(String materialId) {
        return materialPhotoRepository.getByMaterialId(materialId);
    }

    @Override
    public List<MaterialPhotoView> getViewByMaterialId(String materialId) {
        List<MaterialPhoto> materialPhotoList = getByMaterialId(materialId);
        if (materialPhotoList == null || materialPhotoList.isEmpty()) return null;
        List<MaterialPhotoView> materialPhotoViewList = new ArrayList<>();
        for (MaterialPhoto materialPhoto : materialPhotoList) {
            MaterialPhotoView materialPhotoView = new MaterialPhotoView();
            materialPhotoView.setMaterialPhoto(materialPhoto);
            materialPhotoView.setMaterial(materialService.getById(materialPhoto.getMaterialId()));
            materialPhotoViewList.add(materialPhotoView);
        }
        return materialPhotoViewList;
    }


    /**
     * 根据照片ID，得到照片
     *
     * @param materialId
     * @param photoIds
     * @return
     */
    @Override
    public List<MaterialPhotoView> getMaterialPhotoViewListByMaterialIdAndPhotoIds(String materialId,
                                                                                   String[] photoIds) {


        if (materialId==null || photoIds == null || photoIds.length==0) return null;
        List<MaterialPhoto> materialPhotoList =new ArrayList<>();
        List<String> photoIdList = new ArrayList<>();

        //从数据库中查找，说明照片是材料的样本照片
        for (String photoId : photoIds) {
            MaterialPhoto materialPhoto = materialPhotoRepository.getByFilePathLike(photoId);
            if (materialPhoto != null && materialPhoto.getMaterialId().equals(materialId)) {
                materialPhotoList.add(materialPhoto);
                photoIdList.add(photoId);
            }
        }

        //从临时文件中查找，说明照片是临时照片
        for (String photoId : photoIds) {
            if(photoIdList.contains(photoId))continue;
            MaterialPhoto materialPhoto = materialPhotoRepository.getByFilePathLike(photoId);
            if (materialPhoto != null && materialPhoto.getMaterialId().equals(materialId)) {
                materialPhotoList.add(materialPhoto);
                photoIdList.add(photoId);
            }
        }



        List<MaterialPhotoView> materialPhotoViewList = new ArrayList<>();
        for (MaterialPhoto materialPhoto : materialPhotoList) {
            MaterialPhotoView materialPhotoView = new MaterialPhotoView();
            materialPhotoView.setMaterialPhoto(materialPhoto);
            materialPhotoView.setMaterial(materialService.getById(materialPhoto.getMaterialId()));
            materialPhotoViewList.add(materialPhotoView);
        }
        return materialPhotoViewList;

    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialPhoto> getPage(int pageNo, int pageSize) {
        return materialPhotoRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<MaterialPhoto> getPageByMaterialId(String materialId, int pageNo, int pageSize) {
        return materialPhotoRepository.getPageByMaterialId(materialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialPhotoView> getPageView(int pageNo, int pageSize) {
        Page<MaterialPhoto> materialPhotoPage = getPage(pageNo, pageSize);
        return convertMaterialPhotoPage2PageView(materialPhotoPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<MaterialPhotoView> getPageViewByMaterialId(String materialId, int pageNo, int pageSize) {
        Page<MaterialPhoto> materialPhotoPage = getPageByMaterialId(materialId, pageNo, pageSize);
        return convertMaterialPhotoPage2PageView(materialPhotoPage, pageNo, pageSize);
    }

    @Override
    public String addMaterialPhotoTempFile(String uploadPhotoFilesDir, MultipartFile multipartFile) {
        if (uploadPhotoFilesDir == null || uploadPhotoFilesDir.isEmpty()) return null;
        if (multipartFile == null || multipartFile.isEmpty()) return null;
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1
        );

        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase()))
            throw new BusinessException("请选择jpg,jpeg,gif,png格式的图片");

        return FileUtils.addAdditionalFile(uploadPhotoFilesDir, multipartFile, true);
    }

    @Override
    public boolean deleteMaterialPhotoTempFile(String uploadPhotoFilesDir, String fileName) {
        if (uploadPhotoFilesDir == null || uploadPhotoFilesDir.isEmpty()) return false;
        if (fileName == null || fileName.isEmpty()) return false;
        return FileUtils.deleteAdditionalFile(uploadPhotoFilesDir, fileName, true);
    }

    @Override
    public String downloadPhotoFile(String materialPhotoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MaterialPhoto materialPhoto = getById(materialPhotoId);
        if (materialPhoto == null)
            throw new BusinessException("参数错误");

        //1.检查文件是否存在，如果不存在，则报错
        String fileName = ConfigConstant.FilePath + File.separator + materialPhoto.getFilePath();
        boolean fileExist = FileUtils.isFileExist(fileName);
        if (!fileExist)
            throw new BusinessException("参数错误");


        //2.下载文件

        InputStream inputStream = new FileInputStream(fileName);


        String outFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ";" + "filename*=utf-8''" + outFileName);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //  OutputStream outputStream = response.getOutputStream();

        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }


        //生成图片验证码
        //   BufferedImage image = producer.createImage(code);


        //      ImageIO.write(image, "jpg", outputStream);

        outputStream.flush();
        outputStream.close();

        Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Imag = str + Base64.encodeBase64String(outputStream.toByteArray());
        return base64Imag;


    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private MaterialPhotoView getMaterialPhotoViewByMaterialPhotoId(String id) {
        MaterialPhoto materialPhoto = getById(id);
        if (materialPhoto == null) return null;
        MaterialPhotoView materialPhotoView = new MaterialPhotoView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param materialPhotoPage 页面对象
     */
    private Page<MaterialPhotoView> convertMaterialPhotoPage2PageView(Page<MaterialPhoto> materialPhotoPage, int pageNo, int pageSize) {
        if (materialPhotoPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialPhotoView> list = new ArrayList<>();
        for (MaterialPhoto materialPhoto : materialPhotoPage.getResult()) {
            MaterialPhotoView materialPhotoView = getMaterialPhotoViewByMaterialPhotoId(materialPhoto.getId());
            if (materialPhotoView != null) list.add(materialPhotoView);
        }
        return new Page<>(startIndex, materialPhotoPage.getTotalCount(), pageSize, list);
    }

}