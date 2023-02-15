import { defineStore } from "pinia";
import { TagViewState } from "../interface";

const useTagStore = defineStore("useTagStore", {
  state: (): TagViewState => ({
    visitedViews: [],
  }),
  actions: {
    addVisitedView(view: any) {
      if (this.visitedViews.some((v) => v.path === view.path)) return;
      if (view.meta && view.meta.affix) {
        this.visitedViews.unshift(
          Object.assign({}, view, {
            title: view.meta?.title || "no-name",
          })
        );
      } else {
        this.visitedViews.push(
          Object.assign({}, view, {
            title: view.meta?.title || "no-name",
          })
        );
      }
    },
    delVisitedView(view: any) {
      return new Promise((resolve) => {
        for (const [i, v] of this.visitedViews.entries()) {
          if (v.path === view.path) {
            this.visitedViews.splice(i, 1);
            break;
          }
        }
        resolve([...this.visitedViews]);
      });
    },
    delOtherVisitedViews(view: any) {
      return new Promise((resolve) => {
        this.visitedViews = this.visitedViews.filter((v) => {
          return v.meta?.affix || v.path === view.path;
        });
        resolve([...this.visitedViews]);
      });
    },
    updateVisitedView(view: any) {
      for (let v of this.visitedViews) {
        if (v.path === view.path) {
          v = Object.assign(v, view);
          break;
        }
      }
    },
    delLeftViews(view: any) {
      return new Promise((resolve) => {
        const currIndex = this.visitedViews.findIndex((v) => v.path === view.path);
        if (currIndex === -1) {
          return;
        }
        this.visitedViews = this.visitedViews.filter((item, index) => {
          // affix:true 固定tag，例如“首页”
          if (index >= currIndex || (item.meta && item.meta.affix)) {
            return true;
          }
          return false;
        });
        resolve({
          visitedViews: [...this.visitedViews],
        });
      });
    },
    delRightViews(view: any) {
      return new Promise((resolve) => {
        const currIndex = this.visitedViews.findIndex((v) => v.path === view.path);
        if (currIndex === -1) {
          return;
        }
        this.visitedViews = this.visitedViews.filter((item, index) => {
          // affix:true 固定tag，例如“首页”
          if (index <= currIndex || (item.meta && item.meta.affix)) {
            return true;
          }
          return false;
        });
        resolve({
          visitedViews: [...this.visitedViews],
        });
      });
    },
    addView(view: any) {
      this.addVisitedView(view);
    },
    delView(view: any) {
      return new Promise((resolve) => {
        this.delVisitedView(view);
        resolve({
          visitedViews: [...this.visitedViews],
        });
      });
    },
    delOtherViews(view: any) {
      return new Promise((resolve) => {
        this.delOtherVisitedViews(view);
        resolve({
          visitedViews: [...this.visitedViews],
        });
      });
    },
    delAllViews() {
      return new Promise((resolve) => {
        const affixTags = this.visitedViews.filter((tag) => tag.meta?.affix);
        this.visitedViews = affixTags;
        resolve({
          visitedViews: [...this.visitedViews],
        });
      });
    },
  },
  getters: {},
});

export default useTagStore;
