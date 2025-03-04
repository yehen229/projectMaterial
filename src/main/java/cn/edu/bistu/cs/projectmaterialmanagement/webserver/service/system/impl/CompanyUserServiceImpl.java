package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.ICompanyUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.office.ExcelUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompanyUserServiceImpl implements ICompanyUserService {

    private static final Logger log = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

    private final ICompanyUserRepository companyUserRepository;
    private final IUserService userService;
    private final ICompanyService companyService;


    public CompanyUserServiceImpl(ICompanyUserRepository companyUserRepository, IUserService userService, ICompanyService companyService) {
        this.companyUserRepository = companyUserRepository;
        this.userService = userService;
        this.companyService = companyService;
    }

    /**
     * 增加
     */
    @Override
    public String add(CompanyUser companyUser) {
        return companyUserRepository.add(companyUser);
    }

    @Override
    public String addCompanyUserForm(CompanyUserForm companyUserForm) {
        if (companyUserForm == null || companyUserForm.getCompanyId() == null || companyUserForm.getUserRealName() == null || companyUserForm.getTel() == null)
            throw new BusinessException("添加公司用户，发生参数错误");


        try {

            User user = new User();
            user.setUserName(companyUserForm.getTel());
            user.setRealName(companyUserForm.getUserRealName());
            user.setTel(companyUserForm.getTel());
            user.setEmail(companyUserForm.getEmail());
            user.setPassword("123456");

            String userId = userService.add(user);

            if (userId != null && !userId.isEmpty()) {
                CompanyUser companyUser = new CompanyUser();
                companyUser.setCompanyId(companyUserForm.getCompanyId());
                companyUser.setUserId(userId);

                return add(companyUser);
            }
        } catch (Exception e) {
            throw new BusinessException("添加人员错误，发生异常");
        }
        return null;
    }

    @Override
    public int addUsersFromExcel(MultipartFile multipartFile) throws Exception {
        if (!multipartFile.isEmpty()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream());
            Workbook workbook = WorkbookFactory.create(bufferedInputStream);
            return ProcessExcel(workbook);
        }

        return 0;
    }

    /**
     * 删除
     */
    @Override
    public int delete(CompanyUser companyUser) {
        return companyUserRepository.delete(companyUser);
    }

    /**
     * 更新
     */
    @Override
    public int update(CompanyUser companyUser) {
        return companyUserRepository.update(companyUser);
    }

    @Override
    public int updateCompanyUserForm(CompanyUserForm companyUserForm) {
        if (companyUserForm == null || companyUserForm.getCompanyUserId() == null || companyUserForm.getCompanyId() == null || companyUserForm.getUserRealName() == null || companyUserForm.getTel() == null)
            throw new BusinessException("更新公司用户，发生参数错误");

        String companyUserId = companyUserForm.getCompanyUserId();
        CompanyUser companyUser = companyUserRepository.getById(companyUserId);
        if (companyUser == null)
            throw new BusinessException("更新公司用户，发生参数错误");

        User user = userService.getById(companyUser.getUserId());
        if (user == null)
            throw new BusinessException("更新公司用户，发生参数错误");


        user.setEmail(companyUserForm.getEmail());
        user.setRealName(companyUserForm.getUserRealName());
        user.setTel(companyUserForm.getTel());
        user.setEmail(companyUserForm.getEmail());
        int ret = userService.update(user);
        if (ret == 0)
            throw new BusinessException("更新公司用户，发生参数错误");


        companyUser.setCompanyId(companyUserForm.getCompanyId());
        return update(companyUser);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return companyUserRepository.deleteById(id);
    }

    /**
     * 根据companyId删除记录
     *
     * @param companyId
     */
    @Override
    public int deleteByCompanyId(String companyId) {
        return companyUserRepository.deleteByCompanyId(companyId);
    }

    /**
     * 根据userId删除记录
     *
     * @param userId
     */
    @Override
    public int deleteByUserId(String userId) {
        return companyUserRepository.deleteByUserId(userId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return companyUserRepository.getCount();
    }

    /**
     * 根据companyId得到数量
     *
     * @param companyId
     */
    @Override
    public int getCountByCompanyId(String companyId) {
        return companyUserRepository.getCountByCompanyId(companyId);
    }

    /**
     * 根据userId得到数量
     *
     * @param userId
     */
    @Override
    public int getCountByUserId(String userId) {
        return companyUserRepository.getCountByUserId(userId);
    }

    /**
     * 根据id得到CompanyUser
     *
     * @param id
     */
    @Override
    public CompanyUser getById(String id) {
        return companyUserRepository.getById(id);
    }

    @Override
    public Company getCompanyById(String id) {
        CompanyUser companyUser = getById(id);
        if (companyUser != null)
            return companyService.getById(companyUser.getCompanyId());
        return null;
    }

    @Override
    public Company getCompanyByUserId(String userId) {
        CompanyUser companyUser = companyUserRepository.getByUserId(userId);
        if (companyUser != null) {
            return companyService.getById(companyUser.getCompanyId());
        }
        return null;
    }

    /**
     * 判断是否是设计单位员工
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isDesignCompanyByUserId(String userId) {
        Company company = getCompanyByUserId(userId);
        if (company != null)
            return (company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_DESIGN));
        return false;
    }

    /**
     * 判断是否是设计部员工
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isDesignDepartmentByUserId(String userId) {
        Company company = getCompanyByUserId(userId);
        if (company != null)
            return (company.getName().equals(ICompanyService.COMPANY_NAME_DESIGN_DEPARTMENT)
                    && company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_CONSTRUCTION));
        return false;
    }

    /**
     * 判断是否是工程部员工
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isEngineeringDepartmentByUserId(String userId) {
        Company company = getCompanyByUserId(userId);
        if (company != null)
            return (company.getName().equals(ICompanyService.COMPANY_NAME_ENGINEERING_DEPARTMENT)
                    && company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_CONSTRUCTION));
        return false;
    }

    /**
     * 判断是否是监理单位员工
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isSupervisionCompanyByUserId(String userId) {
        Company company = getCompanyByUserId(userId);
        if (company != null)
            return (company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_SUPERVISION));
        return false;
    }

    /**
     * 判断是否是建设单位员工
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isGeneralContractorCompanyByUserId(String userId) {
        Company company = getCompanyByUserId(userId);
        if (company != null)
            return (company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_GENERAL_CONSTRUCTION));
        return false;
    }

    /**
     * 根据companyId得到CompanyUser
     *
     * @param companyId
     */
    @Override
    public List<CompanyUser> getByCompanyId(String companyId) {
        return companyUserRepository.getByCompanyId(companyId);
    }

    @Override
    public List<CompanyUser> getByCompanyType(String companyType) {
        return companyUserRepository.getByCompanyType(companyType);
    }

    /**
     * 根据userId得到CompanyUser
     *
     * @param userId
     */
    @Override
    public CompanyUser getByUserId(String userId) {
        return companyUserRepository.getByUserId(userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<CompanyUser> getPage(int pageNo, int pageSize) {
        return companyUserRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param companyId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<CompanyUser> getPageByCompanyId(String companyId, int pageNo, int pageSize) {
        return companyUserRepository.getPageByCompanyId(companyId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<CompanyUser> getPageByUserId(String userId, int pageNo, int pageSize) {
        return companyUserRepository.getPageByUserId(userId, pageNo, pageSize);
    }

    @Override
    public Page<CompanyUser> getPageByUserName(String userName, int pageNo, int pageSize) {
        return companyUserRepository.getPageByUserName(userName, pageNo, pageSize);
    }

    @Override
    public Page<CompanyUser> getPageByProjectName(String projectName, int pageNo, int pageSize) {
        return companyUserRepository.getPageByProjectName(projectName, pageNo, pageSize);
    }

    @Override
    public Page<CompanyUser> getPageByCompanyName(String companyName, int pageNo, int pageSize) {
        return companyUserRepository.getPageByCompanyName(companyName, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<CompanyUserView> getPageView(int pageNo, int pageSize) {
        Page<CompanyUser> companyUserPage = getPage(pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param companyId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<CompanyUserView> getPageViewByCompanyId(String companyId, int pageNo, int pageSize) {
        Page<CompanyUser> companyUserPage = getPageByCompanyId(companyId, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<CompanyUserView> getPageViewByUserId(String userId, int pageNo, int pageSize) {
        Page<CompanyUser> companyUserPage = getPageByUserId(userId, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 根据用户名称获得指定页面视图数据
     *
     * @param userName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<CompanyUserView> getPageViewByUserName(String userName, Integer pageNo, Integer pageSize) {
        Page<CompanyUser> companyUserPage = getPageByUserName(userName, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 根据项目名称获得视图页面
     *
     * @param projectName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<CompanyUserView> getPageViewByProjectName(String projectName, Integer pageNo, Integer pageSize) {
        Page<CompanyUser> companyUserPage = getPageByProjectName(projectName, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 根据公司名称获得视图页面
     *
     * @param companyName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<CompanyUserView> getPageViewByCompanyName(String companyName, Integer pageNo, Integer pageSize) {
        Page<CompanyUser> companyUserPage = getPageByCompanyName(companyName, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    @Override
    public Page<CompanyUserView> getPageViewByCompanyType(String companyType, Integer pageNo, Integer pageSize) {
        Page<CompanyUser> companyUserPage = getPageByCompanyType(companyType, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    @Override
    public Page<CompanyUserView> getPageViewByCompanyNameAndType(String companyName, String companyType, Integer pageNo, Integer pageSize) {
        Page<CompanyUser> companyUserPage = getPageByNameAndType(companyName, companyType, pageNo, pageSize);
        return convertCompanyUserPage2PageView(companyUserPage, pageNo, pageSize);
    }

    /**
     * 以Excel文件格式下载所有的公司用户
     *
     * @param request
     * @param response
     */
    @Override
    public void downloadAllCompanyUser(HttpServletRequest request, HttpServletResponse response) {

        //获得当前用户
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户为空");

        List<CompanyUser> companyUserList = companyUserRepository.getAllList();
        generateCompanyUserExcel(response, companyUserList);


    }

    /**
     * 以Excel文件格式下载根据用户名称搜索到的公司用户
     *
     * @param userName 用户名称
     * @param request
     * @param response
     */
    @Override
    public void downloadCompanyUserByUserName(String userName, HttpServletRequest request, HttpServletResponse response) {
//获得当前用户
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户为空");

        List<CompanyUser> companyUserList = companyUserRepository.getListByUserName(userName);
        generateCompanyUserExcel(response, companyUserList);
    }

    /**
     * 以Excel文件格式下载根据项目名称搜索到的公司用户
     *
     * @param projectName 项目名称
     * @param request
     * @param response
     */
    @Override
    public void downloadCompanyUserByProjectName(String projectName, HttpServletRequest request, HttpServletResponse response) {
//获得当前用户
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户为空");

        List<CompanyUser> companyUserList = companyUserRepository.getListByProjectName(projectName);
        generateCompanyUserExcel(response, companyUserList);
    }

    /**
     * 以Excel文件格式下载根据公司名称搜索到的公司用户
     *
     * @param companyName 公司名称
     * @param request
     * @param response
     */
    @Override
    public void downloadCompanyUserByCompanyName(String companyName, HttpServletRequest request, HttpServletResponse response) {
//获得当前用户
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户为空");

        List<CompanyUser> companyUserList = companyUserRepository.getListByCompanyName(companyName);
        generateCompanyUserExcel(response, companyUserList);
    }

    private Page<CompanyUser> getPageByNameAndType(String companyName, String companyType, Integer pageNo, Integer pageSize) {
        return companyUserRepository.getPageByNameAndType(companyName, companyType, pageNo, pageSize);
    }

    private Page<CompanyUser> getPageByCompanyType(String companyType, Integer pageNo, Integer pageSize) {
        return companyUserRepository.getPageByCompanyType(companyType, pageNo, pageSize);
    }

    private void generateCompanyUserExcel(HttpServletResponse response,
                                          List<CompanyUser> companyUserList) {
        try {
            ExcelUtils excelUtils = new ExcelUtils(response);
            excelUtils.begin();

            XSSFSheet sheetCompanyUser = excelUtils.createSheet("用户名单");

            //表头
            List<String> heads = new ArrayList<>();
            heads.add("序号");
            heads.add("姓名");
            heads.add("电话");
            heads.add("邮箱");
            heads.add("单位");
            heads.add("单位类型");


            int rowIndex = 0;
            int span = heads.size() - 1;//合并的单元格
            rowIndex = excelUtils.writeTitle(sheetCompanyUser, rowIndex, "用户名单", span);

            rowIndex = excelUtils.writeHeadsToExcel(sheetCompanyUser, heads, rowIndex);


            int i = 1;
            for (CompanyUser companyUser : companyUserList) {
                CompanyUserView companyUserView = getCompanyUserViewByCompanyUserId(companyUser.getId());
                if (companyUserView == null) continue;

                String[] row = new String[heads.size()];

                User user = companyUserView.getUser();
                Company company = companyUserView.getCompany();

                row[0] = i + "";//序号
                row[1] = user.getUserName();//姓名
                row[2] = user.getTel();//电话
                row[3] = user.getEmail();//邮箱
                row[4] = company.getName();//单位
                row[5] = company.getCompanyType();//单位类型


                rowIndex = excelUtils.writeRow(sheetCompanyUser, new ArrayList<>(Arrays.asList(row)), rowIndex);


                i++;
            }

            //自动调整列宽
            excelUtils.autoSizeColumns(sheetCompanyUser, 10);


            excelUtils.end();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int ProcessExcel(Workbook workbook) {
        int nCount = workbook.getNumberOfSheets();
        int nInsertCount = 0;//增加的记录数

        for (int sheetindex = 0; sheetindex < nCount; sheetindex++) {
            Sheet sheet = workbook.getSheetAt(sheetindex);

            int rowstart = sheet.getFirstRowNum();
            int rowEnd = sheet.getLastRowNum();

            int nameIndex = -1;//‘姓名’在Excel文件中的索引
            int telIndex = -1;//‘电话’在Excel文件中的索引
            int emailIndex = -1;//‘邮箱’在Excel文件中的索引
            int companyIndex = -1;//‘单位名称’在Excel文件中的索引


            for (int i = rowstart; i <= rowEnd; i++) {

                Row row = sheet.getRow(i);
                if (null == row) continue;

                int cellStart = row.getFirstCellNum();
                int cellEnd = row.getLastCellNum();

                String nameValue = null;
                String telValue = null;
                String emailValue = null;
                String companyValue = null;

                for (int k = cellStart; k <= cellEnd; k++) {
                    Cell cell = row.getCell(k);
                    if (null == cell) continue;


                    String strIndexName = new DataFormatter().formatCellValue(cell).trim();

                    if (i == rowstart) {
                        //如果是第一行，则该行是表头，格式为：姓名|电话|邮箱|单位名称|...，注意，次序可能不固定
                        if (strIndexName.equals("姓名") || strIndexName.equals("名称") || strIndexName.equals("名字"))
                            nameIndex = k;
                        else if (strIndexName.equals("电话") || strIndexName.equalsIgnoreCase("tel") || strIndexName.equalsIgnoreCase("telephone"))
                            telIndex = k;
                        else if (strIndexName.equals("邮箱") || strIndexName.equalsIgnoreCase("email") || strIndexName.equalsIgnoreCase("email") || strIndexName.equalsIgnoreCase("e-mail"))
                            emailIndex = k;
                        else if (strIndexName.equals("单位") || strIndexName.equals("单位名称") || strIndexName.equals("公司") || strIndexName.equals("公司名称"))
                            companyIndex = k;


                    } else if (k == nameIndex) nameValue = strIndexName;//姓名
                    else if (k == telIndex) telValue = strIndexName;//名称
                    else if (k == emailIndex) emailValue = strIndexName;//邮箱
                    else if (k == companyIndex) companyValue = strIndexName;//单位


                }

                if (nameValue != null && !nameValue.trim().isEmpty() && telValue != null && !telValue.trim().isEmpty()) {


                    try {
                        //1.查询单位有没有。没有的话，不能增加新用户（不能简单增加单位，因为不知道单位性质）
                        Company company = companyService.getByName(companyValue);
                        if (company == null || company.getId() == null) {
                            continue;
                        }

                        //2.添加公司用户
                        CompanyUserForm companyUserForm = new CompanyUserForm();
                        companyUserForm.setCompanyId(company.getId());
                        companyUserForm.setTel(telValue);
                        companyUserForm.setUserRealName(nameValue);
                        companyUserForm.setEmail(emailValue);

                        if (addCompanyUserForm(companyUserForm) != null) nInsertCount++;

                    } catch (Exception e) {
                        log.info("添加人员错误，发生异常");
                    }
                }
            }
        }

        return nInsertCount;
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private CompanyUserView getCompanyUserViewByCompanyUserId(String id) {
        CompanyUser companyUser = getById(id);
        if (companyUser == null) return null;
        CompanyUserView companyUserView = new CompanyUserView();
        companyUserView.setCompany(companyService.getById(companyUser.getCompanyId()));
        companyUserView.setCompanyUser(companyUser);
        companyUserView.setUser(userService.getById(companyUser.getUserId()));
        return companyUserView;
    }


    /**
     * 将页面转换为视图页面
     *
     * @param companyUserPage 页面对象
     */
    private Page<CompanyUserView> convertCompanyUserPage2PageView(Page<CompanyUser> companyUserPage, int pageNo, int pageSize) {
        if (companyUserPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<CompanyUserView> list = new ArrayList<>();
        for (CompanyUser companyUser : companyUserPage.getResult()) {
            CompanyUserView companyUserView = getCompanyUserViewByCompanyUserId(companyUser.getId());
            if (companyUserView != null) list.add(companyUserView);
        }
        return new Page<>(startIndex, companyUserPage.getTotalCount(), pageSize, list);
    }

}