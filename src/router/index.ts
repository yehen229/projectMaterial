import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "Login",
    meta: {
      title: "登录",
      keepAlive: true,
      requireAuth: false,
    },
    component: () => import("@/views/accounts/Login.vue"),
  },
  {
    path: "/scan",
    name: "Scan",
    component: () => import("@/views/project/qrcode/Scan.vue"),
  },
  {
    path: "/",
    name: "Index",
    meta: {
      title: "首页",
      keepAlive: true,
      requireAuth: true,
    },
    component: () => import("@/views/Index.vue"),
    children: [
      {
        path:"logsManage",
        component: () => import("@/views/project/LogsManage.vue"),

      },
      {
        path:"statisticalanalysis",
        component: () => import("@/views/project/Statisticalanalysis.vue"),
      },
      {
        path:"scanAfter",
        component: () => import("@/views/project/qrcode/ScanAfter.vue"),
      },
      {
        path: "scanQrcode",
        component: () => import("@/views/project/qrcode/ScanQrcode.vue"),
      },
      {
        path: "showqrcode",
        component: () => import("@/views/project/qrcode/ShowQrcode.vue"),
      },
      //个人设置
      {
        path: "userInfo",
        component: () => import("@/views/accounts/UserInfo.vue"),
      },

      //个人重置密码
      {
        path: "resetpwd",
        component: () => import("@/views/accounts/UserResetPwd.vue"),
      },

      //单位管理
      {
        path: "company",
        component: () => import("@/views/system/CompanyManager.vue"),
      },

      //单位管理-系统管理员
      {
        path: "company-user",
        component: () => import("@/views/system/CompanyUserManager.vue"),
      },

      //分类管理
      {
        path: "classify",
        component: () => import("@/views/system/ClassifyManager.vue"),
      },

      // 项目列表
      {
        path: "project-list",
        component: () => import("@/views/project/project/ProjectList.vue"),
      },

      // 监理单位位项目列表
      {
        path: "supervision-company-not-ended-project-list",
        component: () =>
          import(
            "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyProjectList.vue"
          ),
      },

      // 总包单位项目列表
      {
        path: "general-contractor-not-ended-project-list",
        component: () =>
          import(
            "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyProjectList.vue"
          ),
      },

      //项目-详细信息
      {
        path: "project-details/:id",
        component: () => import("@/views/project/project/ProjectDetails.vue"),
      },

      //项目用户管理-系统管理员
      {
        path: "project-user",
        component: () => import("@/views/projectuser/ProjectUserManager.vue"),
      },

      //材料管理
      {
        path: "material",
        component: () => import("@/views/system/material/MaterialManager.vue"),
      },

      //品牌管理
      {
        path: "brand",
        component: () => import("@/views/system/brand/BrandManager.vue"),
      },

      //项目-材料管理
      {
        path: "project-material/:id",
        component: () => import("@/views/project/material/ProjectMaterial.vue"),
      },

      //项目-品牌管理-项目列表
      {
        path: "project-brand-list",
        component: () => import("@/views/project/brand/ProjectList.vue"),
      },

      //项目-品牌管理-具体项目的品牌管理
      {
        path: "project-brand/:id",
        component: () =>
          import("@/views/project/brand/ProjectBrandManager.vue"),
      },

      //项目用户任务
      {
        path: "project-user-task-list",
        component: () => import("@/views/project/flow/ProjectUserTaskList.vue"),
      },

      //用户完成任务
      {
        path: "project-user-completed-task-list",
        component: () =>
          import("@/views/project/flow/ProjectUserCompletedTaskList.vue"),
      },

      {
        //总包单位选择品牌（在备选品牌中选择 或使用新品牌并提供说明），选择物料
        path: "general-contractor-company-new-task-of-brand-selection/:id",
        component: () =>
          import(
            "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyNewTaskOfBrandSelection.vue"
          ),
      },
      {
        //总包单位订购材料
        path: "general-contractor-company-new-task-of-buy-material/:id",
        component: () =>
          import(
            "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyNewTaskOfBuyMaterial.vue"
          ),
      },
      {
        //总包单位订购材料
        path: "general-contractor-company-new-task-of-project-material-acceptance/:id",
        component: () =>
          import(
            "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyNewBatchAcceptance.vue"
          ),
      },

      //项目用户任务
      {
        path: "project-user-task/:id",
        component: () => import("@/views/project/flow/ReviewContainer.vue"),
        children: [
          {
            //管理人员设置设计单位、设计部、工程部项目经理和项目员工
            path: "admin-set-design-and-engineering-employee",
            component: () =>
              import(
                "@/views/project/flow/admin/ProjectAdminSetDesignAndEngineeringDepartmentUser.vue"
              ),
          },
          {
            //管理人员设置监理单位、总包单位项目员工
            path: "admin-set-supervision-and-general-contractor-employee",
            component: () =>
              import(
                "@/views/project/flow/admin/ProjectAdminSetSupervisionAndGeneralContractorEmployee.vue"
              ),
          },
          {
            //设计单位填写项目材料
            path: "design-company-fill-out-project-material/:taskId",
            component: () =>
              import("@/views/project/flow/designcompany/ProjectMaterial.vue"),
          },
          {
            //设计部经理分派任务
            path: "design-department-manager-dispatch/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/projectmaterial/DesignDepartmentManagerDispatch.vue"
              ),
          },
          {
            //设计部项目经理审核
            path: "design-department-manager-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/projectmaterial/DesignDepartmentManagerReview.vue"
              ),
          },
          {
            //设计部员工审核
            path: "design-department-employee-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/projectmaterial/DesignDepartmentEmployeerReview.vue"
              ),
          },
          {
            //设计部经理汇总
            path: "design-department-manager-summary-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/projectmaterial/DesignDepartmentManagerSummary.vue"
              ),
          },
          {
            //工程部项目经理分发施工、监理
            path: "engineering-department-manager-dispatch",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/EngineeringDepartmentManagerDispatch.vue"
              ),
          },
          {
            //总包单位选择品牌（在备选品牌中选择 或使用新品牌并提供说明），选择物料
            path: "general_contractor-company-brand-selection/:taskId",
            component: () =>
              import(
                "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyBrandSelection.vue"
              ),
          },
          {
            //监理对总包单位的品牌和物料等进行审核
            path: "supervision-company-review-brand-selection/:taskId",
            component: () =>
              import(
                "@/views/project/flow/supervisioncompany/SupervisionCompanyBrandSelectionReview.vue"
              ),
          },
          {
            //工程部项目经理对于影响外观的审核进行分派任务
            path: "engineering-department-appearance-manager-dispatch/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/appearance/EngineeringDepartmentManagerDispatch.vue"
              ),
          },
          {
            //工程部项目经理对于影响外观的品牌进行直接审核
            path: "engineering-department-appearance-manager-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/appearance/EngineeringDepartmentManagerReview.vue"
              ),
          },
          {
            //工程部项目员工对于影响外观的品牌进行审核
            path: "engineering-department-appearance-employee-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/appearance/EngineeringDepartmentEmployeerReview.vue"
              ),
          },
          {
            //工程部项目经理对于影响外观的品牌进行直接汇总审核
            path: "engineering-department-appearance-manager-summary-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/appearance/EngineeringDepartmentManagerSummary.vue"
              ),
          },
          {
            //设计单位对于影响外观（总包单位的）品牌和物料）进行审核
            path: "design-company-appearance-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designcompany/DesignCompanyEmployeerReview.vue"
              ),
          },
          {
            //设计部项目经理对于影响外观的审核进行分派任务
            path: "design-department-appearance-manager-dispatch/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/appearance/DesignDepartmentManagerDispatch.vue"
              ),
          },
          {
            //设计部项目经理对于影响外观的品牌进行直接审核
            path: "design-department-appearance-manager-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/appearance/DesignDepartmentManagerReview.vue"
              ),
          },
          {
            //设计部项目员工对于影响外观的品牌进行审核
            path: "design-department-appearance-employee-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/appearance/DesignDepartmentEmployeerReview.vue"
              ),
          },
          {
            //设计部项目经理对于影响外观的品牌进行直接汇总审核
            path: "design-department-appearance-manager-summary-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/designdepartment/appearance/DesignDepartmentManagerSummary.vue"
              ),
          },
          {
            //工程部项目经理对于不影响外观的审核进行分派任务
            path: "engineering-department-not-affect-appearance-manager-dispatch/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/notaffectappearance/EngineeringDepartmentManagerDispatch.vue"
              ),
          },
          {
            //工程部项目经理对于不影响外观的品牌进行直接审核
            path: "engineering-department-not-affect-appearance-manager-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/notaffectappearance/EngineeringDepartmentManagerReview.vue"
              ),
          },
          {
            //工程部项目员工对于不影响外观的品牌进行审核
            path: "engineering-department-not-affect-appearance-employee-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/notaffectappearance/EngineeringDepartmentEmployeerReview.vue"
              ),
          },
          {
            //工程部项目经理对于不影响外观的品牌进行直接汇总审核
            path: "engineering-department-not-affect-appearance-manager-summary-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/notaffectappearance/EngineeringDepartmentManagerSummary.vue"
              ),
          },
          {
            //总包单位订购材料
            path: "general-contractor-buy-material/:taskId",
            component: () =>
              import(
                "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyBuyMaterial.vue"
              ),
          },
         
          {
            //总包单位填写工程材料、设备报验材料
            path: "general-contractor-buy-material-document/:taskId",
            component: () =>
              import(
                "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanySubmitDocumentOfBuyMaterial.vue"
              ),
          },
          {
            //监理和总包单位确实是否需要复试
            path: "supervision-company-decide-whether-to-recheck/:taskId",
            component: () =>
              import(
                "@/views/project/flow/supervisioncompany/SupervisionCompanyDecideWhetherReCheckIsRequired.vue"
              ),
          },
          {
            //总包单位新建项目材料批次验收
            path: "general-contractor-batch-acceptance/:taskId",
            component: () =>
              import(
                "@/views/project/flow/generalcontractorcompany/GeneralContractorCompanyBatchAcceptance.vue"
              ),
          },
          {
            //监理项目对项目材料批次验收进行审核
            path: "supervision-company-batch-acceptance/:taskId",
            component: () =>
              import(
                "@/views/project/flow/supervisioncompany/SupervisionCompanyBatchAcceptanceReview.vue"
              ),
          },
          {
            //工程部项目经理对于项目材料批次验收审核进行分派任务
            path: "engineering-department-batch-acceptance-manager-dispatch/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/acceptance/EngineeringDepartmentManagerDispatch.vue"
              ),
          },
          {
            //工程部项目经理对于项目材料批次验收进行直接审核
            path: "engineering-department-batch-acceptance-manager-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/acceptance/EngineeringDepartmentManagerReview.vue"
              ),
          },
          {
            //工程部项目员工对于项目材料批次验收进行审核
            path: "engineering-department-batch-acceptance-employee-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/acceptance/EngineeringDepartmentEmployeerReview.vue"
              ),
          },
          {
            //工程部项目经理对于项目材料批次验收进行汇总审核
            path: "engineering-department-batch-acceptance-manager-summary-review/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/acceptance/EngineeringDepartmentManagerSummary.vue"
              ),
          },
          {
            //工程部项目经理对于项目材料批次验收进行汇总审核
            path: "engineering-department-manager-close-project/:taskId",
            component: () =>
              import(
                "@/views/project/flow/engineeringdepartment/EngineeringDepartmentManagerEndProject.vue"
              ),
          },
        ],
      },

      /*
      //项目管理
      {
        path: "project/:id",
        component: () => import("@/views/project/course/CourseContainer.vue"),
        children: [
          {
            //教学团队
            path: "teacher",
            component: () =>
              import("@/views/teachingclass/course/CourseTeacher.vue"),
          },
        ],
      },*/

      //个人设置
      {
        path: "/",
        component: () => import("@/views/Welcome.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;
