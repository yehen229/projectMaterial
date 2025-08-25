import { IServerPage, IServerResponseData } from "@/server/types/System";
import { IServerProjectView } from "@/server/types/project/project";
import { axios, BASEURL } from "@/http";
import { serverGetProjectPageView } from "@/server/project/project";
import { ElMessage } from "element-plus";
import { IServerProjectMaterialVerificationDocumentView } from "@/server/types/project/review";
import { exportFileWithFullFilenameWithExt } from "@/server/exportfile";

/**
 * 获取材料信息
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function getListMaterialInfo(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getListMaterialInfo", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

// 通过二维码获取材料信息
export async function getMaterialInfo(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getMaterialInfo", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}
/*通过二维码获取项目信息
* */
export async function getProjectInfo(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getprojectInfo", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}
/*通过qrcode获取项目公司信息
* */
export async function getCompanyInfo(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getCompanyInfo", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function serverGetQrcodeProjectPageView(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getQrcodeImage", {
            params: {
                pageNo: pageNo,
                pageSize: pageSize,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

/*通过qrcode获取项目公司信息
* */
export async function getmaterialbrand(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getbranandpositon", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}
/*通过qrcode获取材料照片表信息
* */
export async function getmaterialPhoto(
    qrcode: string,
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "getmaterialPhotoByqrcode", {
            params: {
                qrcode: qrcode,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function getpageviewbykey(
    projectname: string,
    batch: number,
    materialname: string,
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.qrcode + "page-view-by-key", {
            params: {
                projectname: projectname,
                batch: batch,
                materialname: materialname,
                pageNo: pageNo,
                pageSize: pageSize,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function getFilebyqrcode(
    qrcode: string,
): Promise<
    IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerProjectMaterialVerificationDocumentView[]>
        >(
            BASEURL.qrcode +
            "getfilebyqrcode",
            {
                params: {
                    qrcode: qrcode
                },
            }
        );
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function getBlobPartdata(
    projectId: string,
    projectMaterialVerificationDocumentFileId: string,
    downloadFilename: string
) {
    try {
        let res = await axios.get(
            BASEURL.projectmaterialverificationdocumentfile +
            "download-buy-material-verification-document-file-by-id",
            {
                params: {
                    projectId: projectId,
                    projectMaterialVerificationDocumentFileId:
                        projectMaterialVerificationDocumentFileId,
                },
                responseType: "arraybuffer",
            }
        );

        try {
            let response = peocessfile(
                res,
                downloadFilename
            );
            console.log("response", response);
            return response

        } catch (err) {
            console.log(err);
            throw err;
        }

    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function getIsFactory() {
    try {
        
        let res = await axios.get<
            any
        >(BASEURL.qrcode + "getISFactory");
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }

}

export const peocessfile = (
    res: BlobPart,
    downloadFullFilenameWithExt: string
) => {
    let typeVal;
    let filetype = "";
    //得到downloadfilename的文件后缀
    let file_ext = downloadFullFilenameWithExt
        .substring(downloadFullFilenameWithExt.lastIndexOf("."))
        .toLowerCase();
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
    } else if (file_ext === ".txt") {
        typeVal = "text/plain"
    } else {
        console.log("文件类型不支持导出");
        return;
    }
    console.log("BlobPart", res);
    let blob = new Blob([res], {
        type: typeVal,
    });
    let objectUrl = window.URL.createObjectURL(blob); //创建一个新的url对象
    console.log("objectUrl", objectUrl)

    return objectUrl;
    // let link = document.createElement("a");
    // link.href = objectUrl;
    //
    // let file_name = downloadFullFilenameWithExt;
    // console.log("file_name",file_name);
    // link.download = file_name; //  下载的时候自定义的文件名
    // link.click();
    // window.URL.revokeObjectURL(objectUrl); //为了更好地性能和内存使用状况，应该在适当的时候释放url.
};


export const deleteUrl = (
    url: string
) => {
    // let typeVal;
    // let filetype = "";
    // //得到downloadfilename的文件后缀
    // let file_ext = downloadFullFilenameWithExt
    //     .substring(downloadFullFilenameWithExt.lastIndexOf("."))
    //     .toLowerCase();
    // if (file_ext === ".docx" || file_ext === ".docx") {
    //     typeVal =
    //         "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    // } else if (file_ext === ".xlsx" || file_ext === ".xls") {
    //     typeVal =
    //         "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";
    // } else if (file_ext === ".pptx" || file_ext === ".ppt") {
    //     typeVal =
    //         "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    // } else if (file_ext === ".pdf") {
    //     typeVal = "application/pdf";
    // } else if (file_ext === ".jpeg" || file_ext === ".jpg") {
    //     typeVal = "image/jpeg";
    // } else if (file_ext === ".png") {
    //     typeVal = "image/png";
    // } else if (file_ext === ".zip") {
    //     typeVal = "application/zip";
    // } else {
    //     console.log("文件类型不支持导出");
    //     return;
    // }
    // console.log("BlobPart", res);
    // let blob = new Blob([res], {
    //     type: typeVal,
    // });
    // let objectUrl = window.URL.createObjectURL(blob); //创建一个新的url对象
    // console.log("objectUrl",objectUrl)
    //
    // return objectUrl;
    // let link = document.createElement("a");
    // link.href = objectUrl;
    //
    // let file_name = downloadFullFilenameWithExt;
    // console.log("file_name",file_name);
    // link.download = file_name; //  下载的时候自定义的文件名
    // link.click();
    window.URL.revokeObjectURL(url); //为了更好地性能和内存使用状况，应该在适当的时候释放url.

};

export async function getOnlyBlob(
    projectId: string,
    projectMaterialVerificationDocumentFileId: string,
    downloadFilename: string
) {
    try {
        let res = await axios.get(
            BASEURL.projectmaterialverificationdocumentfile +
            "download-buy-material-verification-document-file-by-id",
            {
                params: {
                    projectId: projectId,
                    projectMaterialVerificationDocumentFileId:
                        projectMaterialVerificationDocumentFileId,
                },
                responseType: "arraybuffer",
            }
        );

        return res;

    } catch (err) {
        console.log(err);
        throw err;
    }
}