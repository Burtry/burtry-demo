import instance from "@/utils/http";

//规则： 服务接口/api/v1/请求路径
export const publishArticleAPI = (data) => {
  return instance({
    method: "post",
    url: "article/api/v1/article",
    data: data
  })
}

export const getArticleListAPI = (id) => {
  return instance({
    method: "get",
    url: `article/api/v1/article/${id}`
  })

}

export const deleteArticleAPI = (id) => {
  return instance({
    method: "delete",
    url: `article/api/v1/article/${id}`
  })
}

export const getOverViewAPI = (id) => {
  return instance({
    method: "get",
    url: `article/api/v1/article/overView/${id}`
  })
}
