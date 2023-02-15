import useAppStore from "./modules/app";
import useTagStore from "./modules/tag";
import useSettingStore from "./modules/setting";
import usePermissionStore from "./modules/permission";
import useUserStore from "./modules/user";

const useStore = () => ({
  tag: useTagStore(),
  app: useAppStore(),
  setting: useSettingStore(),
  user: useUserStore(),
  permission: usePermissionStore(),
});

export default useStore;
