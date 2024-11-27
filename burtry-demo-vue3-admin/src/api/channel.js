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

export const updateChannelStatusAPI = (id) => {
  return instance({
    method: "post",
    url: `api/v1/channel/${id}`
  })
}

export const deleteChannelAPI = (id) => {
  return instance({
    method: "delete",
    url: `api/v1/channel`,
    params: {
      id: id
    }
  })
}

export const updateChannelAPI = (data) => {
  return instance({
    method: "put",
    url: "api/v1/channel",
    data: data
  });

}
