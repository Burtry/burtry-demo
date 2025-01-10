import instance from "@/utils/http";

export const uploadFileAPI = (data) => {
  return new Promise((resolve, reject) => {
    // 定义一个超时定时器
    const timeout = setTimeout(() => {
      reject(new Error("上传失败：请求超时,请重试"));
    }, 3000);

    // 发起请求
    instance({
      url: "/user/api/v1/upload",
      method: "post",
      data,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
      .then((response) => {
        clearTimeout(timeout); // 请求成功，清除定时器
        resolve(response); // 返回响应
      })
      .catch((error) => {
        clearTimeout(timeout); // 请求失败，清除定时器
        reject(error); // 抛出错误
      });
  });
};
