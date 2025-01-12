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
