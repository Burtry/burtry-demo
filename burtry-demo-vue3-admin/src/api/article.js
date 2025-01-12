import instance from "@/utils/http";

export const getChannelListAPI = () => {
  return instance({
    url: "/api/v1/article/channelList",
    method: "get",

  })
}

export const getArticleListAPI = (data) => {
  return instance({
    url: "/api/v1/article/list",
    method: "post",
    data: data
  })
}

export const getByIdAPI = (id) => {
  return instance({
    url: `/api/v1/article/${id}`,
    method: "get"
  })
}

export const updateArticleStatusAPI = (id, status) => {
  return instance({
    url: `/api/v1/article/status`,
    method: "put",
    params: {
      id: id,
      status: status
    }
  })
}
