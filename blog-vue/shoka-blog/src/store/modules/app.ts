interface AppState {
  /** 侧边栏展开 */
  isCollapse: boolean;
  /** 搜索框 */
  searchFlag: boolean;
  /** 登录框 */
  loginFlag: boolean;
  /** 注册框 */
  registerFlag: boolean;
  /** 忘记密码框 */
  forgetFlag: boolean;
  /** 邮箱框 */
  emailFlag: boolean;
  /** 左侧展开 */
  sideFlag: boolean;
}

export const useAppStore = defineStore("useAppStore", {
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
