import {IServerPage, IServerResponseData} from "@/server/types/System";
import {IServerProjectView} from "@/server/types/project/project";
import {axios, BASEURL} from "@/http";
import {serverGetProjectPageView} from "@/server/project/project";
import {ElMessage} from "element-plus";
import {IServerProjectMaterialVerificationDocumentView} from "@/server/types/project/review";
import {exportFileWithFullFilenameWithExt} from "@/server/exportfile";




export async function servergetunpassreviewbyprojectidmaterialid_companyid(
    projectid: string,
    companyid: string
) {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_review_by_projectid_materialid_companyid", {
            params: {
                projectid: projectid,
                companyid: companyid,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function servergetunpassreviewbefore_zongbao(
    projectid: string,
    materialid: string
) {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_review_before_zongbao", {
            params: {
                projectid: projectid,
                materialid: materialid,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function servergetunpassreviewjianli(
    projectid: string,
    materialid: string
) {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_reviewcontent_jianli", {
            params: {
                projectid: projectid,
                materialid: materialid,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function servergetunpassreviewjianliandgongchengbu(
    projectid: string,
    materialid: string
) {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_reviewcontent_jianli_gongchengbu", {
            params: {
                projectid: projectid,
                materialid: materialid,
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function servergetdesignUnpassData(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_material_message", {
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

export async function servergetunpassbeforezongbao(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_revie_before_zongbao", {
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

export async function servergetunpassjianli(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_review_jianli", {
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
export async function servergetunpassjianliandgongchengbu(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_unpass_review_jianli_and_gongchengbu", {
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

export async function serverGetProjectListPageView(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_page_project_list", {
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

export async function byprojectname_getList(
    projectName:string,
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_search_page_project_list", {
            params: {
                projectName:projectName,
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

export async function getAllList_processing(
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_processing_project_count", {
            params: {
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}
export async function getAllList_end(
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "get_end_project_count", {
            params: {
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}

export async function getchart_projectname_totalReviewResulDisagree(
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "getAllList_disagree", {
            params: {
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}