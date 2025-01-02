import instance from "@/utils/http";

//规则： 服务接口/api/v1/请求路径

export const postCommentAPI = (data) => {
  return instance({
    url: '/comment/api/v1/comment',
    method: 'post',
    data: data
  })
}

export const getCommentListAPI = (articleId) => {
  return instance({
    url: '/comment/api/v1/comment',
    method: 'get',
    params: {
      articleId: articleId
    }
  })
}
