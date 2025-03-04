/**
 * 导出文件
 * @param res
 * @param filetype
 * @param downloadFilenameWithoutExt
 */
export const exportFile = (
    res: BlobPart,
    filetype: string,
    downloadFilenameWithoutExt: string
) => {
  let typeVal;
  let file_ext;
  if (filetype === "word") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    file_ext = ".docx";
  } else if (filetype === "excel") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";
    file_ext = ".xlsx";
  } else if (filetype === "powerpoint") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    file_ext = ".pptx";
  } else if (filetype === "pdf") {
    typeVal = "application/pdf";
    file_ext = ".pdf";
  } else if (filetype === "jpeg") {
    typeVal = "image/jpeg";
    file_ext = ".jpg";
  } else if (filetype === "png") {
    typeVal = "image/png";
    file_ext = ".png";
  } else if (filetype === "zip") {
    typeVal = "application/zip";
    file_ext = ".zip";
  }

  let blob = new Blob([res], {
    type: typeVal,
  });
  let objectUrl = window.URL.createObjectURL(blob); //创建一个新的url对象
  let link = document.createElement("a");
  link.href = objectUrl;

  let file_name = downloadFilenameWithoutExt + file_ext;
  console.log(file_name);
  link.download = file_name; //  下载的时候自定义的文件名
  link.click();
  window.URL.revokeObjectURL(objectUrl); //为了更好地性能和内存使用状况，应该在适当的时候释放url.
};

/**
 * 导出文件，要求文件名为完整文件名（带有后缀，如 abc.docx），根据文件名称后缀自动判断文件类型
 * @param res
 * @param downloadFullFilenameWithExt
 * @returns
 */
export const exportFileWithFullFilenameWithExt = (
    res: BlobPart,
    downloadFullFilenameWithExt: string
) => {
  let typeVal;
  let filetype = "";
  //得到downloadfilename的文件后缀
  let file_ext = downloadFullFilenameWithExt
      .substring(downloadFullFilenameWithExt.lastIndexOf("."))
      .toLowerCase();
  console.log("文件类型",file_ext)
  if (file_ext === ".docx" || file_ext === ".doc") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
  } else if (file_ext === ".xlsx" || file_ext === ".xls") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";
  } else if (file_ext === ".pptx" || file_ext === ".ppt") {
    typeVal =
        "application/vnd.openxmlformats-officedocument.presentationml.presentation";
  } else if (file_ext === ".pdf") {
    typeVal = "application/pdf";
  } else if (file_ext === ".jpeg" || file_ext === ".jpg") {
    typeVal = "image/jpeg";
  } else if (file_ext === ".png") {
    typeVal = "image/png";
  } else if (file_ext === ".zip") {
    typeVal = "application/zip";
  } else {
    console.log("文件类型不支持导出");
    return;
  }
  console.log("BlobPart", res);
  let blob = new Blob([res], {
    type: typeVal,
  });
  let objectUrl = window.URL.createObjectURL(blob); //创建一个新的url对象
  console.log("objectUrl",objectUrl)

  let link = document.createElement("a");
  link.href = objectUrl;

  let file_name = downloadFullFilenameWithExt;
  console.log("file_name",file_name);
  link.download = file_name; //  下载的时候自定义的文件名
  link.click();
  window.URL.revokeObjectURL(objectUrl); //为了更好地性能和内存使用状况，应该在适当的时候释放url.
};
