import instance from "@/utils/http";

//规则： 服务接口/api/v1/请求路径

export const searchAPI = (data) => {
  return instance({
    url: '/search/api/v1/search',
    method: 'post',
    data: data
  })
}
