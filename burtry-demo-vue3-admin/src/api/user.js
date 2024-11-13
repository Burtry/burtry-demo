import instance from "@/utils/http";

export const getCodeAPI = () => {
  return instance({
    url: 'api/v1/code',
    method: 'get',
    responseType: 'blob'
  });
};

export const getStringAPI = () => {
  return instance({
    url: 'api/v1/get',
    method: 'get'
  });
};
