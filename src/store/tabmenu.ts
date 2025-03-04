import { defineStore } from "pinia";

export interface tabMenuLabel {
  key: string;
  label: string;
  name: string;
}

export const useTabStore = defineStore("tabStore", {
  state: () => {
    return {
      openTab: [],
      activeTabKey: "/",
    };
  },
  actions: {
    /**
     * 增加Tab，需要检查增加的内容是否存在，如果存在，则激活该Tab；如果不存在，则增加并激活
     * @param tab
     */
    addTab(newTab: tabMenuLabel) {
      const tabs = this.openTab;
      let activeName = this.activeIndex;
      let find = false;

      tabs.forEach((tab, index) => {
        if (tab.key === newTab.key) {
          find = true;
        }
      });
      if (!find) this.openTab.push(newTab);
      this.setActiveTab(newTab.key);
    },
    /**
     * 删除Tab，需要将激活的Tab设置为下一个
     * @param targetName
     */
    deleteTab(targetKey: string) {
      const tabs = this.openTab;
      let active = this.activeTabKey;

      let nextTab = null;
      let index = -1;

      tabs.forEach((tab, index) => {
        if (tab.key === targetKey) {
          nextTab = tabs[index + 1] || tabs[index - 1];
        }
      });
      if (nextTab) this.setActiveTab(nextTab.key);
      else this.setActiveTab("/");
      this.openTab = tabs.filter((tab) => tab.key !== targetKey);
    },
    setActiveTab(key: string) {
      this.activeTabKey = key;
    },
  },
});
