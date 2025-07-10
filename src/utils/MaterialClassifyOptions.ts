import {
  IServerMaterial,
  IServerMaterialBrand,
  IServerMaterialBrandView,
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyDivisionTreeItem,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifyGroupTreeItem,
  IServerMaterialClassifyGroupView,
  IServerMaterialClassifySection,
  IServerMaterialClassifySectionView,
  IServerMaterialClassifyTree,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
} from "@/server/types/system/material";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  serverMaterialClassifyDivisionAdd,
  serverMaterialClassifyDivisionDelete,
  serverMaterialClassifyDivisionDeleteById,
  serverMaterialClassifyDivisionUpdate,
  serverGetMaterialClassifyDivisionById,
  serverGetMaterialClassifyDivisionPage,
  serverGetMaterialClassifyDivisionAllList,
} from "@/server/system/materialclassifydivision";

import {
  serverMaterialClassifyGroupAdd,
  serverMaterialClassifyGroupDelete,
  serverMaterialClassifyGroupDeleteById,
  serverMaterialClassifyGroupUpdate,
  serverGetMaterialClassifyGroupById,
  serverGetMaterialClassifyGroupPage,
  serverGetMaterialClassifyGroupTree,
} from "@/server/system/materialclassifygroup";

import {
  serverMaterialClassifySectionAdd,
  serverMaterialClassifySectionDelete,
  serverMaterialClassifySectionDeleteById,
  serverMaterialClassifySectionUpdate,
  serverGetMaterialClassifySectionById,
  serverGetMaterialClassifyTree,
  serverGetMaterialClassifySectionPage,
} from "@/server/system/materialclassifysection";

export interface IMaterialClassifyOption {
  id: string;
  value: string;
  label: string;
  children: IMaterialClassifyOption[];
}

export interface IMaterialFactoryOption {
  id: string;
  value: string;
  label: string;
}

const generateSectionTree = (
  groupTreeChildren: IServerMaterialClassifySectionView[]
) => {
  if (!groupTreeChildren) return [];
  let temp: IMaterialClassifyOption[] = [];
  groupTreeChildren.forEach((sectionTreeItem) => {
    const newChild: IMaterialClassifyOption = {
      id: sectionTreeItem.materialClassifySection.id,
      label: sectionTreeItem.materialClassifySection.name,
      value: sectionTreeItem.materialClassifySection.id,
      children: [],
    };
    temp.push(newChild);
  });
  return temp;
};

const generateGroupOption = (
  divisionTreeChildren: IServerMaterialClassifyGroupTreeItem[]
) => {
  if (!divisionTreeChildren) return [];
  let temp: IMaterialClassifyOption[] = [];
  divisionTreeChildren.forEach((groupTreeItem) => {
    const children: IMaterialClassifyOption[] = generateSectionTree(
      groupTreeItem.children
    );

    const newChild: IMaterialClassifyOption = {
      id: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.id,
      label: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.name,
      value: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.id,
      children: children,
    };
    temp.push(newChild);
  });
  return temp;
};

const generateDivisitonOption = (tree: IServerMaterialClassifyTree) => {
  const classifyOptions: IMaterialClassifyOption[] = [];

  tree.children.forEach((divisionTreeItem) => {
    const children: IMaterialClassifyOption[] = generateGroupOption(
      divisionTreeItem.children
    );
    const newChild: IMaterialClassifyOption = {
      id: divisionTreeItem.materialClassifyDivision.id,
      label: divisionTreeItem.materialClassifyDivision.name,
      value: divisionTreeItem.materialClassifyDivision.id,
      children: children,
    };

    classifyOptions.push(newChild);
  });
  return classifyOptions;
};

export const generateMaterialClassifyOption = async () => {
  const ret = await serverGetMaterialClassifyTree();

  if (ret && ret.code == 200) {
    return generateDivisitonOption(ret.data);
  }
  return [];
};

export const generateMaterialFactoryOption = async () => {
  const ret = await serverGetMaterialClassifyTree();

  if (ret && ret.code == 200) {
    return generateDivisitonOption(ret.data);
  }
  return [];
};
