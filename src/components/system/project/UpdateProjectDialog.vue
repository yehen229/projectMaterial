<template>
    <el-dialog
      :visible.sync="dialogVisible"
      :title="dialogTitle"
      @close="handleClose"
      width="500px"
    >
      <template #footer>
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirm">确 定</el-button>
      </template>
  
      <div>
        <!-- 输入项 -->
        <el-form :model="formData" ref="formRef">
          <el-form-item label="项目名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入项目名称"></el-input>
          </el-form-item>
          <el-form-item label="项目描述" prop="description">
            <el-input type="textarea" v-model="formData.description" placeholder="请输入项目描述"></el-input>
          </el-form-item>
          <!-- 可以根据需要添加更多的输入项 -->
        </el-form>
      </div>
    </el-dialog>
  </template>
  
  <script setup lang="ts">
  import { ref, watch } from "vue";
  
  // 定义组件的 props
  const props = defineProps({
    dialogVisible: {
      type: Boolean,
      default: false
    },
    dialogTitle: { // 新增一个 title 属性
      type: String,
      default: '编辑项目'
    },
  });
  
  // 定义事件
  const emit = defineEmits(["update:dialogVisible"]);
  
  // 定义表单数据
  const formData = ref({
    name: '',
    description: ''
  });
  
  // 关闭对话框时触发的函数
  const handleClose = () => {
    formData.value = { name: '', description: '' }; // 清空表单数据
    emit("update:dialogVisible", false); // 触发关闭对话框事件
  };
  
  // 点击确定按钮的处理函数
  const handleConfirm = () => {
    // 这里可以添加对数据的处理逻辑
    console.log("Form submitted:", formData.value);
    handleClose(); // 关闭对话框
  };
  
  // 监听 dialogVisible 的变化
  watch(() => props.dialogVisible, (newVal) => {
    if (newVal) {
      // 当对话框显示时，可以在这里初始化数据
      formData.value = { name: '', description: '' }; // 或者从后端获取现有数据
    }
  });
  </script>
  
  <style scoped>
  /* 添加样式以满足您的需求 */
  </style>
  

  //新增一个