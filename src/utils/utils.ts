/**
 * 将给定时间格式化为：YYYY-MM-DD HH:mm:ss
 * @param date
 * @returns
 */
export const formatDate = (date: Date | string | null | undefined) => {
  if (!date) return "";
  const dateMat = new Date(date);

  const year = dateMat.getFullYear() + "";
  const month = dateMat.getMonth() + 1 + "";
  const day = dateMat.getDate() + "";
  const hh = dateMat.getHours() + "";
  const mm = dateMat.getMinutes() + "";
  const ss = dateMat.getSeconds() + "";
  const timeFormat =
    year +
    "-" +
    (month.length == 1 ? "0" + month : month) +
    "-" +
    (day.length == 1 ? "0" + day : day) +
    " " +
    (hh.length == 1 ? "0" + hh : hh) +
    ":" +
    (mm.length == 1 ? "0" + mm : mm) +
    ":" +
    (ss.length == 1 ? "0" + ss : ss);
  return timeFormat;
};

/**
 * 将给定时间格式化为：YYYY-MM-DD
 * @param date
 * @returns
 */
export const formatDate1 = (date: Date | string | null | undefined) => {
  if (!date) return "";
  const dateMat = new Date(date);

  const year = dateMat.getFullYear() + "";
  const month = dateMat.getMonth() + 1 + "";
  const day = dateMat.getDate() + "";
  const hh = dateMat.getHours() + "";
  const mm = dateMat.getMinutes() + "";
  const ss = dateMat.getSeconds() + "";
  const timeFormat =
    year +
    "-" +
    (month.length == 1 ? "0" + month : month) +
    "-" +
    (day.length == 1 ? "0" + day : day);
  return timeFormat;
};

/**
 * 将给定时间格式化为：HH:mm:ss
 * @param date
 * @returns
 */
export const formatDate2 = (date: Date | string | null | undefined) => {
  if (!date) return "";
  const dateMat = new Date(date);

  const year = dateMat.getFullYear() + "";
  const month = dateMat.getMonth() + 1 + "";
  const day = dateMat.getDate() + "";
  const hh = dateMat.getHours() + "";
  const mm = dateMat.getMinutes() + "";
  const ss = dateMat.getSeconds() + "";
  const timeFormat =
    (hh.length == 1 ? "0" + hh : hh) +
    ":" +
    (mm.length == 1 ? "0" + mm : mm) +
    ":" +
    (ss.length == 1 ? "0" + ss : ss);
  return timeFormat;
};

/**
 *将目前时间格式化为：YYYY-MM-DD HH:mm:ss
 */
const getNow = () => {
  return formatDate(new Date());
};

export const genUUID = () => {
  const UUID = crypto.randomUUID();
  return UUID.replace(/-/g, "");
};
