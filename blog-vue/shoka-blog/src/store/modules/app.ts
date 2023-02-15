import { AppState } from "../types";

const useAppStore = defineStore("useAppStore", {
  state: (): AppState => ({
    isCollapse: false,
    searchFlag: false,
    loginFlag: false,
    registerFlag: false,
    forgetFlag: false,
    emailFlag: false,
    sideFlag: false,
  }),
  actions: {
    setCollapse(flag: boolean) {
      this.isCollapse = flag;
    },
    setLoginFlag(flag: boolean) {
      this.loginFlag = flag;
    },
    setRegisterFlag(flag: boolean) {
      this.registerFlag = flag;
    },
    setForgetFlag(flag: boolean) {
      this.forgetFlag = flag;
    },
  },
  getters: {},
  persist: {
    key: "app",
    storage: sessionStorage,
  },
});

export default useAppStore;
