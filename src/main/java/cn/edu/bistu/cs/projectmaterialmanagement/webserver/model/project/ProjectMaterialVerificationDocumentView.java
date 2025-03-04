package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialVerificationDocumentView {
    private List<ProjectMaterialVerificationDocumentFile> projectMaterialVerificationDocumentFileList;
    private ProjectMaterialVerificationDocument projectMaterialVerificationDocument;//t_project_material_verification_document
    private User user;//外键：t_user_id,关联表为：t_user表,
    private BuyMaterialView buyMaterialView;//外键：t_project_material_id,关联表为：t_project_material表,

    public List<ProjectMaterialVerificationDocumentFile> getProjectMaterialVerificationDocumentFileList() {
        return projectMaterialVerificationDocumentFileList;
    }

    public void setProjectMaterialVerificationDocumentFileList(List<ProjectMaterialVerificationDocumentFile> projectMaterialVerificationDocumentFileList) {
        this.projectMaterialVerificationDocumentFileList = projectMaterialVerificationDocumentFileList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectMaterialVerificationDocument getProjectMaterialVerificationDocument() {
        return projectMaterialVerificationDocument;
    }

    public void setProjectMaterialVerificationDocument(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        this.projectMaterialVerificationDocument = projectMaterialVerificationDocument;
    }

    public BuyMaterialView getBuyMaterialView() {
        return buyMaterialView;
    }

    public void setBuyMaterialView(BuyMaterialView buyMaterialView) {
        this.buyMaterialView = buyMaterialView;
    }
}