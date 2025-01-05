import instance from "@/utils/http";

//规则： 服务接口/api/v1/请求路径

export const likeBehaviorAPI = (data) => {
  return instance({
    url: 'behavior/api/v1/behavior/like',
    method: 'post',
    data: data
  })
}

export const getLikesAPI = (articleId) => {
  return instance({
    url: 'behavior/api/v1/behavior/like',
    method: 'get',
    params: {
      articleId: articleId
    }
  })
}
