package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class BrandPublicView {
    private BrandPublic brandPublic;//t_brand_public
    private BrandView brandView;//外键：t_brand_id,关联表为：t_brand表,
    private Company company;
    public BrandPublic getBrandPublic() {
        return brandPublic;
    }

    public void setBrandPublic(BrandPublic brandPublic) {
        this.brandPublic = brandPublic;
    }

    public BrandView getBrandView() {
        return brandView;
    }

    public void setBrandView(BrandView brandView) {
        this.brandView = brandView;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}