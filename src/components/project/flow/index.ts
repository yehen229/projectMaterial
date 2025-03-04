import { IServerProjectUserTask } from "@/server/types/project/flow";
import { ca } from "element-plus/es/locale";

export const getDesignCompanyIndex = (
  projectUserTask: IServerProjectUserTask
) => {
  if (!projectUserTask) return -1;
  switch (projectUserTask.taskDefinitionKey) {
    case "Activity_Design_Company_Submit_Project_Material_0":
      return 0;
    case "Activity_Design_Company_Submit_Project_Material_1":
      return 1;
    case "Activity_Design_Company_Submit_Project_Material_2":
      return 2;
    case "Activity_Design_Company_Submit_Project_Material_3":
      return 3;
    case "Activity_Design_Company_Submit_Project_Material_4":
      return 4;
    case "Activity_Design_Company_Submit_Project_Material_5":
      return 5;
    case "Activity_Design_Company_Submit_Project_Material_6":
      return 6;
    case "Activity_Design_Company_Submit_Project_Material_7":
      return 7;

    case "Activity_Project_Material_Design_Department_Distribution_Review_0":
      return 0;
    case "Activity_Project_Material_Design_Department_Distribution_Review_1":
      return 1;
    case "Activity_Project_Material_Design_Department_Distribution_Review_2":
      return 2;
    case "Activity_Project_Material_Design_Department_Distribution_Review_3":
      return 3;
    case "Activity_Project_Material_Design_Department_Distribution_Review_4":
      return 4;
    case "Activity_Project_Material_Design_Department_Distribution_Review_5":
      return 5;
    case "Activity_Project_Material_Design_Department_Distribution_Review_6":
      return 6;
    case "Activity_Project_Material_Design_Department_Distribution_Review_7":
      return 7;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_0":
      return 0;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_1":
      return 1;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_2":
      return 2;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_3":
      return 3;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_4":
      return 4;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_5":
      return 5;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_6":
      return 6;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_7":
      return 7;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_0":
      return 0;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_1":
      return 1;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_2":
      return 2;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_3":
      return 3;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_4":
      return 4;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_5":
      return 5;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_6":
      return 6;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_7":
      return 7;
    case "Activity_Affect_Appearance_Design_Company_Reivew_0":
      return 0;
    case "Activity_Affect_Appearance_Design_Company_Reivew_1":
      return 1;
    case "Activity_Affect_Appearance_Design_Company_Reivew_2":
      return 2;
    case "Activity_Affect_Appearance_Design_Company_Reivew_3":
      return 3;
    case "Activity_Affect_Appearance_Design_Company_Reivew_4":
      return 4;
    case "Activity_Affect_Appearance_Design_Company_Reivew_5":
      return 5;
    case "Activity_Affect_Appearance_Design_Company_Reivew_6":
      return 6;
    case "Activity_Affect_Appearance_Design_Company_Reivew_7":
      return 7;
  }
  return -1;
};

export const getTaskName = (
  projectUserTask: IServerProjectUserTask | undefined | null
) => {
  if (!projectUserTask) return "";
  switch (projectUserTask.taskDefinitionKey) {
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew":
      //设计部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.designDepartmentEmployee)
        return "设计部该项目的项目员工对项目材料审核";
      else if (projectUserTask.designDepartmentManager)
        return "设计部该项目项目经理及项目员工对项目材料审核,目前暂无项目员工填写审核意见，项目经理可以直接审核";
      break;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.designDepartmentEmployee)
        return "设计部该项目项目员工对项目材料审核";
      else if (projectUserTask.designDepartmentManager)
        return " 项目经理汇总员工意见，对项目材料审核";
      break;
    case "Activity_Affect_Appearance_Engineering_Department_Remain_Employee_Reivew":
      //工程部经理对工程部项目员工审核结果进行汇总审核
      if (projectUserTask.engineeringDepartmentManager)
        return "工程部项目经理对于影响外观（总包单位的）品牌和物料进行汇总审核";
      else if (projectUserTask.engineeringDepartmentEmployee)
        return "工程部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;
    case "Activity_Affect_Appearance_Design_Company_Reivew":
      //设计单位对于影响外观（总包单位的）品牌和物料）进行审核
      return "设计单位对于影响外观（总包单位的）品牌和物料进行审核";

    case "Activity_Affect_Appearance_Design_Department_Distribution_Review":
      //设计部对于影响外观（总包单位的）品牌和物料）进行分派任务
      return "设计部对于影响外观（总包单位的）品牌和物料进行分派任务";

    case "Activity_Affect_Appearance_Design_Department_Manager_And_Employee_Reivew":
      // 设计部项目员工对于影响外观（总包单位的）品牌和物料）进行审核
      return "设计部项目员工对于影响外观（总包单位的）品牌和物料进行审核";

    case "Activity_Affect_Appearance_Design_Department_Remain_Employee_Reivew":
      if (projectUserTask.designDepartmentManager)
        return "设计部项目经理对于影响外观（总包单位的）品牌和物料进行汇总审核";
      else if (projectUserTask.designCompanyEmployee)
        return "设计部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;
    default:
      return projectUserTask.taskName;
  }
};

export const getTaskTitle = (projectUserTask: IServerProjectUserTask) => {
  if (!projectUserTask) return "";
  switch (projectUserTask.taskDefinitionKey) {
    case "Activity_Construction_Company_Create_Project":
      //建设单位创建项目，启动流程
      return "建设单位创建项目，启动流程";

    case "Activity_Admin_Set_Design_Company_And_Design_Department_And_Engineering_Department_Employees":
      //管理人员设置设计、工程部员工阶段
      return "管理人员设置设计、工程部员工阶段";

    case "Activity_Design_Company_Submit_Project_Material":
      //设计单位项目材料填报阶段
      return "设计单位填报项目材料";

    case "Activity_Project_Material_Design_Department_Distribution_Review":
      //设计部经理分派任务
      return "设计部项目经理分派项目物料审核任务";

    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew":
      //设计部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.designDepartmentManager)
        return "设计部项目经理填写审核意见，对项目材料审核";
      else if (projectUserTask.designDepartmentEmployee)
        return "设计部该项目项目员工填写审核意见，对项目材料审核";
      break;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.designDepartmentManager)
        return "项目经理汇总员工意见，对项目材料审核";
      else if (projectUserTask.designDepartmentEmployee)
        return "设计部该项目项目员工填写审核意见，对项目材料审核";
      break;
    case "Activity_Engineering_Department_Manager_Dispatch":
      //工程部项目经理分发施工、监理
      return "工程部项目经理分发监理单位与总包单位";

    case "Activity_Admin_Set_SuperVision_And_General_Contractor_Employees":
      //管理人员设置监理、总包单位员工阶段
      return "管理人员设置监理、总包单位员工阶段";

    case "Activity_General_Contractor_Company_Select_Brand":
      //总包单位选择品牌（在备选品牌中选择 或使用新品牌并提供说明），选择物料
      return "总包单位选择品牌（在备选品牌中选择 或使用新品牌并提供说明），选择物料";

    case "Activity_Supervision_Company_Review_Select_Brand":
      //监理对总包单位的品牌和物料等进行审核
      return "监理对总包单位的品牌和物料等进行审核";

    case "Activity_Affect_Appearance_Engineering_Department_Distribution_Review":
      //工程部经理对于影响外观（总包单位的）品牌和物料）进行分派任务
      return "工程部经理对于影响外观（总包单位的）品牌和物料分派审核";

    case "Activity_Affect_Appearance_Engineering_Department_Manager_And_Employee_Reivew":
      //工程部项目员工对于影响外观（总包单位的）品牌和物料）进行审核
      if (projectUserTask.engineeringDepartmentManager)
        return "工程部项目经理对于影响外观（总包单位的）品牌和物料）进行审核";
      else if (projectUserTask.engineeringDepartmentEmployee)
        return "工程部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;

    case "Activity_Affect_Appearance_Engineering_Department_Remain_Employee_Reivew":
      //工程部经理对工程部项目员工审核结果进行汇总审核

      if (projectUserTask.engineeringDepartmentManager)
        return "工程部项目经理对于影响外观（总包单位的）品牌和物料进行汇总审核";
      else if (projectUserTask.engineeringDepartmentEmployee)
        return "工程部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;

    case "Activity_Affect_Appearance_Design_Company_Reivew":
      //设计单位对于影响外观（总包单位的）品牌和物料）进行审核
      return "设计单位对于影响外观（总包单位的）品牌和物料进行审核";

    case "Activity_Affect_Appearance_Design_Department_Distribution_Review":
      //设计部对于影响外观（总包单位的）品牌和物料）进行分派任务
      return "设计部对于影响外观（总包单位的）品牌和物料进行分派任务";

    case "Activity_Affect_Appearance_Design_Department_Manager_And_Employee_Reivew":
      // 设计部项目员工对于影响外观（总包单位的）品牌和物料）进行审核
      if (projectUserTask.designDepartmentManager)
        return "设计部项目经理对于影响外观（总包单位的）品牌和物料）进行审核";
      else if (projectUserTask.designCompanyEmployee)
        return "设计部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;

    case "Activity_Affect_Appearance_Design_Department_Remain_Employee_Reivew":
      //设计部经理对设计部项目员工审核结果进行汇总审核
      if (projectUserTask.designDepartmentManager)
        return "设计部项目经理对于影响外观（总包单位的）品牌和物料进行汇总审核";
      else if (projectUserTask.designCompanyEmployee)
        return "设计部项目员工填写审核意见，对于影响外观（总包单位的）品牌和物料进行审核";
      break;

    case "Activity_Not_Affect_Appearance_Engineering_Department_Distribution_Review":
      //工程部经理对于不影响外观（总包单位的）品牌和物料）进行分派任务
      return "工程部经理对于不影响外观（总包单位的）品牌和物料分派审核";

    case "Activity_Not_Affect_Appearance_Engineering_Department_Manager_And_Employee_Reivew":
      //工程部项目员工对于不影响外观（总包单位的）品牌和物料）进行审核
      if (projectUserTask.engineeringDepartmentManager)
        return "工程部项目经理对于不影响外观（总包单位的）品牌和物料）进行审核";
      else if (projectUserTask.engineeringDepartmentEmployee)
        return "工程部项目员工填写审核意见，对于不影响外观（总包单位的）品牌和物料进行审核";
      break;

    case "Activity_Not_Affect_Appearance_Engineering_Department_Remain_Employee_Reivew":
      //工程部经理对工程部项目员工审核结果进行汇总审核

      if (projectUserTask.engineeringDepartmentManager)
        return "工程部项目经理对于不影响外观（总包单位的）品牌和物料进行汇总审核";
      else if (projectUserTask.engineeringDepartmentEmployee)
        return "工程部项目员工填写审核意见，对于不影响外观（总包单位的）品牌和物料进行审核";
      break;
  }
};

export const getTaskProjectName = (projectUserTask: IServerProjectUserTask) => {
  if (!projectUserTask) return "";
  return projectUserTask.projectView.project.name;
};
