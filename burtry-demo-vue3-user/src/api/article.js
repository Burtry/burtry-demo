import instance from "@/utils/http";

export const publishArticleAPI = (data) => {
  return instance({
    method: "post",
    url: "article/api/v1/article",
    data: data
  })
}
