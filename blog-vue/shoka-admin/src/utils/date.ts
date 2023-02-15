import dayjs from "dayjs";

export function formatDate(date: string | Date | null, format = "YYYY-MM-DD"): string {
  if (date == null) {
    return "";
  }
  return dayjs(date).format(format);
}

export function formatDateTime(date: string | Date, format = "YYYY-MM-DD HH:mm:ss"): string {
  return formatDate(date, format);
}
