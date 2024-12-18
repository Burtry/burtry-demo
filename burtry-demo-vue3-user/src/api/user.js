import instance from "@/utils/http"

export const getCodeAPI = (username) => {
  return instance({
    url: '/user/api/v1/code',
    method: 'get',
    responseType: 'blob',
    params: {
      username
    }
  });
};


export const registerAPI = (data) => {
  return instance({
    url: '/user/api/v1/register',
    method: 'post',
    data: data
  });
};

export const loginAPI = (params) => {
  return instance({
    url: '/user/api/v1/login',
    method: 'get',
    params: params
  });
}

export const getStringAPI = () => {
  return instance({
    url: '/user/api/v1/get',
    method: 'get'
  })
}

export const getUserByIdAPI = (id) => {
  return instance({
    url: `/user/api/v1/${id}`,
    method: 'get'
  })
}
