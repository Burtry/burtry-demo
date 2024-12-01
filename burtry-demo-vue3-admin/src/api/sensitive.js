import instance from "@/utils/http";

export const getSensitiveListAPI = (params) => {
  return instance({
    url: "api/v1/sensitive",
    method: "get",
    params: params
  })
}

export const addSensitiveAPI = (sensitiveName) => {

  return instance({
    url: "api/v1/sensitive",
    method: "post",
    params: sensitiveName
  })
}

export const deleteSensitiveAPI = (id) => {
  return instance({
    url: "api/v1/sensitive",
    method: "delete",
    params: {
      id: id
    }
  })
}

export const updateSensitiveAPI = (data) => {
  return instance({
    url: "api/v1/sensitive",
    method: "put",
    data: data
  })
}
