import instance from "@/utils/http"

export const getCodeAPI = () => {
    return instance({
        url: '/admin/code',
        method: 'get'
    })

}
