interface DefaultSettings {
  /**
   * 是否显示 tagView
   */
  tagView: boolean;
  /**
   * 是否固定头部
   */
  fixedHeader: boolean;
  /**
   * 是否显示Logo
   */
  sidebarLogo: boolean;
}

const defaultSettings: DefaultSettings = {
  tagView: true,
  fixedHeader: true,
  sidebarLogo: true,
};

export default defaultSettings;
