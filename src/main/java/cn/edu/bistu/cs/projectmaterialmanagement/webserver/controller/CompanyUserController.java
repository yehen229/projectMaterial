package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("companyuser/v1")
@EnableMethodSecurity
public class CompanyUserController {
    private final ICompanyUserService companyUserService;
    private final IProjectUserService projectUserService;
    private final IUserService userService;

    CompanyUserController(ICompanyUserService companyUserService,
                          IProjectUserService projectUserService,
                          IUserService userService) {
        this.companyUserService = companyUserService;
        this.projectUserService = projectUserService;
        this.userService = userService;
    }

    /**
     * 通过id获取公司用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "get-by-id")
    public CompanyUser getById(@RequestParam(value = "id") String id) {
        return companyUserService.getById(id);
    }


    /**
     * 通过公司id获取公司用户
     *
     * @param companyId 公司ID
     * @return 公司用户列表
     */
    @GetMapping(value = "get-by-company-id")
    public List<CompanyUser> getByCompanyId(@RequestParam(value = "companyId") String companyId) {
        return companyUserService.getByCompanyId(companyId);
    }


    /**
     * 通过用户id获取公司用户
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "get-by-user-id")
    public CompanyUser getByUserId(@RequestParam(value = "userId") String userId) {
        return companyUserService.getByUserId(userId);
    }

    /**
     * 用户是否是总包单位员工
     *
     * @return
     */
    @GetMapping(value = "get-current-login-user-is-general-contractor-company-employee-by-user-id")
    public boolean isGeneralContractorCompanyEmployee() {
        User user = userService.getCurrentLoginUser();
        if (user == null) return false;
        return companyUserService.isGeneralContractorCompanyByUserId(user.getId());
    }


    /**
     * 增加公司用户
     *
     * @param companyUser
     * @return
     */
    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody CompanyUser companyUser) {
        return companyUserService.add(companyUser);
    }

    /**
     * 添加公司用户
     *
     * @param companyUserForm
     * @return
     */
    @PostMapping(value = "add-company-user-form")
    @PreAuthorize("hasRole('Admin')")
    public String addCompanyUserForm(@RequestBody CompanyUserForm companyUserForm) {
        return companyUserService.addCompanyUserForm(companyUserForm);
    }

    /**
     * 通过Excel文件导入公司用户
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping(value = "add-company-user-excel")
    @PreAuthorize("hasRole('Admin')")
    public int addUsersFromExcel(
            @RequestParam("file")
            MultipartFile multipartFile)
            throws Exception {
        return companyUserService.addUsersFromExcel(multipartFile);
    }

    /**
     * 删除公司用户
     *
     * @param companyUser
     * @return
     */
    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody CompanyUser companyUser) {
        return companyUserService.delete(companyUser);
    }

    /**
     * 更新公司用户
     *
     * @param companyUser
     * @return
     */
    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody CompanyUser companyUser) {
        return companyUserService.update(companyUser);
    }

    @PostMapping(value = "update-company-user-form")
    @PreAuthorize("hasRole('Admin')")
    public int updateCompanyUserForm(@RequestBody CompanyUserForm companyUserForm) {
        return companyUserService.updateCompanyUserForm(companyUserForm);
    }


    /**
     * 按页获取公司用户
     *
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping(value = "page")
    public Page<CompanyUser> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return companyUserService.getPage(pageNo, pageSize);
    }

    /**
     * 按页获取公司用户视图
     *
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping(value = "page-view")
    public Page<CompanyUserView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageView(pageNo, pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }

    /**
     * 按页获取公司用户视图
     *
     * @param userName 用户名称
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping(value = "page-view-by-user-name")
    public Page<CompanyUserView> getPageViewByUserName(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByUserName(userName, pageNo,
                                                                                             pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }


    /**
     * 按页获取公司用户视图
     *
     * @param projectName 项目名称
     * @param pageNo      页码
     * @param pageSize    页面大小
     * @return
     */
    @GetMapping(value = "page-view-by-project-name")
    public Page<CompanyUserView> getPageViewByProjectName(
            @RequestParam(value = "projectName", required = true) String projectName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByProjectName(projectName, pageNo,
                                                                                                pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }

    /**
     * 获得包含指定公司名称的指定页面的公司用户视图
     *
     * @param companyName 公司名称
     * @param pageNo      页码
     * @param pageSize    页面大小
     * @return
     */
    @GetMapping(value = "page-view-by-company-name")
    public Page<CompanyUserView> getPageViewByCompanyName(
            @RequestParam(value = "companyName", required = true) String companyName,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByCompanyName(companyName, pageNo,
                                                                                                pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-company-type")
    public Page<CompanyUserView> getPageViewByCompanyType(
            @RequestParam(value = "companyType", required = true) String companyType,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByCompanyType(companyType, pageNo,
                                                                                                pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }


    @GetMapping(value = "page-view-by-company-id")
    public Page<CompanyUserView> getPageViewByCompanyId(
            @RequestParam(value = "companyId", required = true) String companyId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByCompanyId(companyId, pageNo,
                                                                                              pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-company-name-and-type")
    public Page<CompanyUserView> getPageViewByCompanyNameAndType(
            @RequestParam(value = "companyName", required = true) String companyName,
            @RequestParam(value = "companyType", required = true) String companyType,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<CompanyUserView> companyUserViewPage = companyUserService.getPageViewByCompanyNameAndType(companyName,
                                                                                                       companyType,
                                                                                                       pageNo,
                                                                                                       pageSize);
        return projectUserService.convert(companyUserViewPage, pageNo, pageSize);
    }


    @GetMapping(value = "download-company-user-all")
    @PreAuthorize("hasAnyRole('Admin')")
    public DownloadFile downloadAllCompanyUser(
            HttpServletRequest request,
            HttpServletResponse response) {
        companyUserService.downloadAllCompanyUser(request, response);

        return new DownloadFile();
    }

    @GetMapping(value = "download-company-user-by-user-name")
    @PreAuthorize("hasAnyRole('Admin')")
    public DownloadFile downloadCompanyUserByUserName(
            @RequestParam(value = "userName", required = true) String userName,
            HttpServletRequest request,
            HttpServletResponse response) {
        companyUserService.downloadCompanyUserByUserName(userName, request, response);

        return new DownloadFile();
    }

    @GetMapping(value = "download-company-user-by-project-name")
    @PreAuthorize("hasAnyRole('Admin')")
    public DownloadFile downloadCompanyUserByProjectName(
            @RequestParam(value = "projectName", required = true) String projectName,
            HttpServletRequest request,
            HttpServletResponse response) {
        companyUserService.downloadCompanyUserByProjectName(projectName, request, response);

        return new DownloadFile();
    }

    @GetMapping(value = "download-company-user-by-company-name")
    @PreAuthorize("hasAnyRole('Admin')")
    public DownloadFile downloadCompanyUserByCompanyName(
            @RequestParam(value = "companyName", required = true) String companyName,
            HttpServletRequest request,
            HttpServletResponse response) {
        companyUserService.downloadCompanyUserByCompanyName(companyName, request, response);

        return new DownloadFile();
    }

}