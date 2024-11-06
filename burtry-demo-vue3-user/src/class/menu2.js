class MyButtonMenu1 {                       // JS 语法
  constructor() {
    this.title = '暂存草稿' // 自定义菜单标题
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
    console.log("暂存文章");

  }
}

export default MyButtonMenu1
