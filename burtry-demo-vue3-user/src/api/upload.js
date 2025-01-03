import instance from "@/utils/http";

export const uploadFileAPI = (data) => {
  return instance({
    url: "/user/api/v1/upload",
    method: "post",
    data,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}
