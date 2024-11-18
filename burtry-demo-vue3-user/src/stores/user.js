import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("user", () => {
    const userInfo = ref({})

    //移除用户信息
    const removeUserInfo = () => {
        userInfo.value = {}
    }

    return {
        userInfo,
        removeUserInfo
    }
}, {
    persist: true
})