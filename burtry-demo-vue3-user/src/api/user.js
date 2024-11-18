import instance from "@/utils/http"

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


export const registerAPI = (data) => {
  return instance({
    url: 'api/v1/register',
    method: 'post',
    data: data
  });
};
