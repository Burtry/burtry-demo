class MyButtonMenu2 {
  constructor() {
    this.title = '发布文章' // 自定义菜单标题
    this.tag = 'button'
  }

  getValue() {
  }
  isActive() {
    return false
  }

  isDisabled() {
    return false
  }

  // 点击菜单时触发的函数
  exec() {
    console.log('发布文章')

  }
}

export default MyButtonMenu2
