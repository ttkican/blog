import { ElMessageBox, ElNotification } from "element-plus";

export function notifySuccess(message: string) {
  ElNotification({
    title: "成功",
    message,
    type: "success",
  });
}

export function messageConfirm(content: string) {
  return ElMessageBox.confirm(content, "系统提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    center: true,
    type: "warning",
  });
}
