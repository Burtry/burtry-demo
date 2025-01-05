import instance from "@/utils/http";

//规则： 服务接口/api/v1/请求路径

export const likeBehaviorAPI = (data) => {
  return instance({
    url: 'behavior/api/v1/behavior/like',
    method: 'post',
    data: data
  })
}

export const getDataAPI = (articleId) => {
  return instance({
    url: 'behavior/api/v1/behavior/data',
    method: 'get',
    params: {
      articleId: articleId
    }
  })
}
export const readBehaviorAPI = (articleId) => {
  return instance({
    url: 'behavior/api/v1/behavior/read',
    method: 'post',
    params: {
      articleId: articleId
    }
  })

}

export const collectBehaviorAPI = (data) => {
  return instance({
    url: 'behavior/api/v1/behavior/collect',
    method: 'post',
    data: data
  })
}
