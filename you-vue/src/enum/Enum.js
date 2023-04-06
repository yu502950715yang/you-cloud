export class Enum {
  /**
   * 添加枚举字段
   * @param {string} field  枚举类名
   * @param {string} label  枚举名称
   * @param {Object} value  枚举值
   */
  add(field, label, value) {
    this[field] = { label, value }
    return this
  }

  /**
   * 根据枚举value获取其label
   */
  getLabelByValue(value) {
    // 字段不存在返回‘’
    if (value === undefined || value === null) {
      return ''
    }
    for (const i in this) {
      const e = this[i]
      if (e && e.value === value) {
        return e.label
      }
    }

    return ''
  }

  /**
   * 根据枚举field获取其value
   */
  getValueByField(field) {
    // 字段不存在返回‘’
    if (field === undefined || field === null) {
      return ''
    }
    for (const i in this) {
      debugger
      const e = this[i]
      if (e && e.field === field) {
        return e.value
      }
    }
    return ''
  }
}
