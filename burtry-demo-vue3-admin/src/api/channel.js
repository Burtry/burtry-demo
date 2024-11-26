import instance from "@/utils/http";

export const addChannelAPI = (data) => {
  return instance({
    method: "post",
    url: "api/v1/channel",
    data: data
  });
}

export const getChannelListAPI = (params) => {
  return instance({
    method: "get",
    url: "api/v1/channel",
    params: params
  });
}
