import {IServerPage, IServerResponseData} from "@/server/types/System";
import {IServerProjectView} from "@/server/types/project/project";
import {axios, BASEURL} from "@/http";
import {serverGetProjectPageView} from "@/server/project/project";
import {ElMessage} from "element-plus";
import {IServerProjectMaterialVerificationDocumentView} from "@/server/types/project/review";
import {exportFileWithFullFilenameWithExt} from "@/server/exportfile";


export async function serverGetlogListPageView(
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.logManage + "page", {
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


export async function byprojectname_Search(
    projectName:string,
    pageNo: number,
    pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
    try {
        let res = await axios.get<
            any,
            IServerResponseData<IServerPage<IServerProjectView>>
        >(BASEURL.logManage + "searchpage", {
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