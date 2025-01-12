import instance from "@/utils/http";

export const getCodeAPI = (username) => {
  return instance({
    url: 'api/v1/code',
    method: 'get',
    responseType: 'blob',
    params: {
      username
    }
  });
};

export const loginAPI = (params) => {
  return instance({
    url: 'api/v1/login',
    method: 'get',
    params: params
  });
}

export const getUserListAPI = (params) => {
  return instance({
    url: 'api/v1/user',
    method: 'get',
    params: params
  });
}

export const updateUserStatusAPI = (id) => {
  return instance({
    url: 'api/v1/user',
    method: 'put',
    params: {
      id: id
    }
  })
}

//重置密码
export const resetPasswordAPI = (id) => {
  return instance({
    url: 'api/v1/user/rePassword',
    method: 'put',
    params: {
      id: id
    }
  })
}
