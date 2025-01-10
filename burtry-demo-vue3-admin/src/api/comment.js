import instance from "@/utils/http";

export const getCommentListAPI = (params) => {
  return instance({
    url: "/api/v1/comment",
    method: "get",
    params: params
  })
}

export const getCommentByIdAPI = (id) => {
  return instance({
    url: `/api/v1/comment/${id}`,
    method: "get"
  })
}

export const deleteCommentAPI = (id) => {
  return instance({
    url: `/api/v1/comment/${id}`,
    method: "delete"
  })
}
