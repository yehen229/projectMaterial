<template>
  <div>
    <el-form 
      ref="projectFormRef" 
      :model="sizeForm" 
      :rules="rules" 
      label-width="80px"
    >
      <el-page-header @back="goBack" style="margin-bottom: 20px">
        <template #content>
          <span class="text-large font-600 mr-3">项目修改</span>
        </template>
      </el-page-header>

      <el-form-item label="项目名称" prop="name">
        <el-input v-model="sizeForm.name" placeholder="请输入项目名称"></el-input>
      </el-form-item>

      <el-form-item label="活动区域" prop="location">
        <el-select v-model="sizeForm.location" placeholder="请选择活动区域">
          <el-option label="其他" value="other"></el-option>
          <el-option label="北京" value="beijing"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="总投资" prop="totalTaxIncluded">
        <el-input v-model.number="sizeForm.totalTaxIncluded" placeholder="总投资"></el-input>
      </el-form-item>

      <el-form-item label="建筑面积（地上）" prop="buildingAreaAboveGround">
        <el-input v-model.number="sizeForm.buildingAreaAboveGround" placeholder="限制数字（平米）" type="number"></el-input>
      </el-form-item>

      <el-form-item label="建筑面积（地下）" prop="buildingAreaUnderGround">
        <el-input v-model.number="sizeForm.buildingAreaUnderGround" placeholder="限制数字（平米）" type="number"></el-input>
      </el-form-item>

      <el-form-item label="建设单位" prop="companyConstructionId">
        <el-select v-model="sizeForm.companyConstructionId" placeholder="请选择建设单位">
          <el-option label="单位A" value="unitA"></el-option>
          <el-option label="单位B" value="unitB"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="设计单位" prop="companyDesignId">
        <el-select v-model="sizeForm.companyDesignId" placeholder="请选择设计单位">
          <el-option label="单位C" value="unitC"></el-option>
          <el-option label="单位D" value="unitD"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="其他" prop="note">
        <el-input v-model="sizeForm.note" placeholder="请输入其他信息"></el-input>
      </el-form-item>

      <el-form-item label="项目立项时间" prop="createDatetime">
        <el-col :span="11">
          <el-date-picker 
            type="date" 
            placeholder="选择日期" 
            v-model="sizeForm.createDatetime" 
            style="width: 100%;"
          />
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">确认修改</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';

export default {
  setup() {
      // 表单引用
      const projectFormRef = ref();

      // 表单数据
      const sizeForm = reactive({
          name: '',
          location: '',
          totalTaxIncluded: null,
          buildingAreaAboveGround: null,
          buildingAreaUnderGround: null,
          companyConstructionId: '',
          companyDesignId: '',
          note: '',
          createDatetime: ''
      });

      // 表单验证规则
      const rules = reactive({
          name: [
              { required: true, message: '请输入项目名称', trigger: 'blur' }
          ],
          location: [
              { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          totalTaxIncluded: [
              { required: true, message: '请输入总投资', trigger: 'blur' }
          ],
          buildingAreaAboveGround: [
              { required: true, message: '请输入地上建筑面积', trigger: 'blur' }
          ],
          buildingAreaUnderGround: [
              { required: true, message: '请输入地下建筑面积', trigger: 'blur' }
          ],
          companyConstructionId: [
              { required: true, message: '请选择建设单位', trigger: 'change' }
          ],
          companyDesignId: [
              { required: true, message: '请选择设计单位', trigger: 'change' }
          ],
          createDatetime: [
              { required: true, message: '请选择项目立项时间', trigger: 'change' }
          ]
      });

      // 提交表单
      const submitForm = () => {
          projectFormRef.value?.validate((valid) => {
              if (valid) {
                  ElMessage.success('修改成功');
                  // 此处可以补充提交表单数据的逻辑
              } else {
                  ElMessage.error('表单验证失败');
              }
          });
      };

      // 重置表单
      const resetForm = () => {
          projectFormRef.value?.resetFields();
      };

      return {
          projectFormRef,
          sizeForm,
          rules,
          submitForm,
          resetForm
      };
  }
};
</script>

<style scoped>
/* 你的样式 */
</style>
