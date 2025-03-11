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



export async function serverGetProjectListPageView(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "getList", {
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
        >(BASEURL.statisticalanalysis + "byprojectname-getList", {
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

export async function getAllList_agree(
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "getAllList_agree", {
            params: {
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}
export async function getAllList_disagree(
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

export async function getchart_projectname_totalReviewResulDisagree(
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.statisticalanalysis + "getchart_projectname_totalReviewResulDisagree", {
            params: {
            },
        });
        return res;
    } catch (err) {
        console.log(err);
        throw err;
    }
}