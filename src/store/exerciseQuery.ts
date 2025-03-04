import { defineStore } from 'pinia'
import { IServerQuestionView } from '@/server/ServerType'
export const useExerciseQueryStore = defineStore({
  id: 'exerciseQuery', // id必填，且需要唯一
  state: () => {
    return {
      knowledgePoint: '',
      hasKnowledgePoint: false,
      difficulty: '',
      hasDifficulty: false,
      type: '',
      hasType: false,
      title: '',
      hasTitle: false,
      content: '',
      hasContent: false,
      sysUser: '',
      hasSysUser: false,
      tagId: '',
      hasTagId: false,
      exerciseState: '',
      hasExerciseState: false,

      pageNo: 1, //目前页数
      pageTotalCount: 0, //总的数量，由于每页只有一个数据，因此也是总页数
      pageCanPrev: false, //是否可以前向移动
      pageCanNext: false, //是否可以后向移动

      questionFirst: null as IServerQuestionView | null,
      questionLast: null as IServerQuestionView | null,
    }
  },
  actions: {
    setKnowledgePoint(val: string) {
      this.knowledgePoint = val
    },
    setHasKnowledgePoint(val: boolean) {
      this.hasKnowledgePoint = val
    },
    setDifficulty(val: string) {
      this.difficulty = val
    },
    setHasDifficulty(val: boolean) {
      this.hasDifficulty = val
    },
    setType(val: string) {
      this.type = val
    },
    setHasType(val: boolean) {
      this.hasType = val
    },
    setTitle(val: string) {
      this.title = val
    },
    setHasTitle(val: boolean) {
      this.hasTitle = val
    },
    setContent(val: string) {
      this.content = val
    },
    setHasContent(val: boolean) {
      this.hasContent = val
    },
    setSysUser(val: string) {
      this.sysUser = val
    },
    setHasSysUser(val: boolean) {
      this.hasSysUser = val
    },
    setTagId(val: string) {
      this.tagId = val
    },
    setHasTagId(val: boolean) {
      this.hasTagId = val
    },
    setExerciseState(val: string) {
      this.exerciseState = val
    },
    setHasExerciseState(val: boolean) {
      this.hasExerciseState = val
    },
    setPageNo(val: number) {
      this.pageNo = val
    },
    setPageTotalCount(val: number) {
      this.pageTotalCount = val
    },
    setPageCanPrev(val: boolean) {
      this.pageCanPrev = val
    },
    setPageCanNext(val: boolean) {
      this.pageCanNext = val
    },

    setQuestionFirst(val: IServerQuestionView | null) {
      this.questionFirst = val
    },
    setQuestionLast(val: IServerQuestionView | null) {
      this.questionLast = val
    },
    set(
      knowledgePoint: string,
      hasKnowledgePoint: boolean,
      difficulty: string,
      hasDifficulty: boolean,
      type: string,
      hasType: boolean,
      title: string,
      hasTitle: boolean,
      content: string,
      hasContent: boolean,
      sysUser: string,
      hasSysUser: boolean,
      tagId: string,
      hasTagId: boolean,
      exerciseState: string,
      hasExerciseState: boolean,
    ) {
      this.knowledgePoint = knowledgePoint
      this.hasKnowledgePoint = hasKnowledgePoint
      this.difficulty = difficulty
      this.hasDifficulty = hasDifficulty
      this.type = type
      this.hasType = hasType
      this.title = title
      this.hasTitle = hasTitle
      this.content = content
      this.hasContent = hasContent
      this.sysUser = sysUser
      this.hasSysUser = hasSysUser
      this.tagId = tagId
      this.hasTagId = hasTagId
      this.exerciseState = exerciseState
      this.hasExerciseState = hasExerciseState
    },
  },
})
