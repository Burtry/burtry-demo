class MyButtonMenu3 {                       // JS 语法
  constructor() {
    this.title = '定时发布' // 自定义菜单标题
    this.tag = 'button'
  }
  // editor
  getValue() {
  }

  isActive() {
    return false
  }


  isDisabled() {
    return false
  }

  // 点击菜单时触发的函数
  // editor, value
  exec() {
    console.log("定时发布");

  }
}

export default MyButtonMenu3
