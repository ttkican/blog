import useStore from "@/store";
import { Directive, DirectiveBinding } from "vue";

/**
 * v-hasPerm 按钮权限处理
 */
export const hasPerm: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    // 管理员角色
    const { user } = useStore();
    if (user.roleList.includes("1")) {
      return true;
    }
    // 其他角色
    const { value } = binding;
    if (value) {
      // 按钮权限标识
      const requiredPerms = value;
      const hasPerm = user.permissionList?.some((perm) => {
        return requiredPerms.includes(perm);
      });
      if (!hasPerm) {
        el.parentNode && el.parentNode.removeChild(el);
      }
    } else {
      throw new Error("need perms! Like v-has-perm=\"['sys:user:add','sys:user:edit']\"");
    }
  },
};

/**
 * v-hasRole 角色权限处理
 */
export const hasRole: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding;
    if (value) {
      const requiredRoles = value;
      const { user } = useStore();
      const hasRole = user.roleList.some((role) => {
        return requiredRoles.includes(role);
      });

      if (!hasRole) {
        el.parentNode && el.parentNode.removeChild(el);
      }
    } else {
      throw new Error("need roles! Like v-has-role=\"['admin','test']\"");
    }
  },
};
