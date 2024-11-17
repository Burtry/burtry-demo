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

export const getStringAPI = () => {
  return instance({
    url: 'api/v1/get',
    method: 'get'
  });
};

export const loginAPI = (params) => {
  return instance({
    url: 'api/v1/login',
    method: 'get',
    params: params
  });
}
