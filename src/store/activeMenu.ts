import { defineStore } from "pinia";

export const useActiveMenuStore = defineStore("activeMenu", {
  state: () => {
    return {
      courseActiveTab: "", //Course中激活的tab
    };
  },
  actions: {
    setCourseActiveTab(name: string) {
      this.courseActiveTab = name;
    },
    setCourseActiveTeacher() {
      this.courseActiveTab = "teacher";
    },
    setCourseActiveStudent() {
      this.courseActiveTab = "student";
    },
    setCourseActiveHomework() {
      this.courseActiveTab = "homework";
    },
    setCourseActiveExam() {
      this.courseActiveTab = "exam";
    },
  },
});
