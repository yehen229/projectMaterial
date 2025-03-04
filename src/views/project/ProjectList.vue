<template>
    <el-page-header @back="goBack" style="margin-bottom: 20px">
        <template #content>
            <span class="text-large font-600 mr-3">项目列表</span>
        </template>
        <div class="mt-4 text-sm font-bold"></div>
    </el-page-header>

    <div class="tab-container">
        <div class="top-toolbar">
            <!--搜索框-->
            <div class="input-with-select">
                <el-input v-model="searchText" placeholder="输入搜索内容">
                    <template #append>
                        <el-button :icon="Search" @click="onSearchClick" />
                    </template>
                </el-input>
            </div>
        </div>

        <!--显示内容-->
        <div class="project-container">
            <div class="project-card" v-for="(proj, i) in tableData">
                <div class="project-name">
                    {{proj.name}}
                </div>

                <div class="project-details">
                    <div class="start-end-time">
                        项目起止时间：{{ proj.createDatetime }}-{{ proj.deletedAt }}
                    </div>

                    <div>
                        项目其他概要信息
                    </div>
                </div>
            </div>
        </div>

        <!-- 分页 -->
        <el-pagination :hide-on-single-page="true" class="page-class" background v-model:current-page="pageNo"
            v-model:page-size="pageSize" :page-sizes="[10, 50, 100, 200, 300, 400]"
            layout="total, sizes, prev, pager, next" :total="totalCount" @prev-click="onPagePrevClick"
            @next-click="onPageNextClick" @current-change="onPageCurrentChange" @size-change="onPageSizeChange" />
    </div>
</template>

<style scoped>
.page-class {
    padding: 10px;
}

.top-toolbar {
    display: flex;
}

.project-card {
    border: 1.335px;
    border-radius: 4px;
    border-style: solid;
    border-color: #dcdfe6;
    margin-top: 20px;
}

.project-name {
    margin: 15px 10px;
}

.project-details {
    display: flex;
    margin-left: 15px;
    margin-bottom: 10px;
    font-size: 11px;
}

.start-end-time {
    margin-right: 20px;
}
</style>

<script setup lang="ts">

import { ref, onMounted } from "vue";
import axios from "axios";

import {
    getUserPageSize,
} from "@/cookies/user";

const apiUrl = "http://127.0.0.1:8000/project/v1/page";

// 状态变量
const tableData = ref<any[]>([]);
const totalCount = ref<number>(0);

// 页码和页大小
const pageNo = ref(1);
const pageSize = ref(getUserPageSize());

const searchText = ref("");

// 获取数据的函数
const fetchTableData = async () => {
    try {
        const response = await axios.get(apiUrl, {
            params: {
                pageNo: pageNo.value,
                pageSize: pageSize.value
            }
        });
        console.log(response.data);

        tableData.value = response.data.result;
        totalCount.value = response.data.totalCount;
    } catch (error) {
        console.error("Failed to fetch data", error);
    }
};

// 在组件挂载时获取数据
onMounted(() => {
    fetchTableData();
});

const goBack = () => {
    history.back();
};

</script>