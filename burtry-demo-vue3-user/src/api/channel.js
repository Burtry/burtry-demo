import instance from "@/utils/http";

export const getChannelListAPI = () => {
  return instance({
    url: "/user/api/v1/channel",
    method: "get",
  });
}
