/**
 * @Description: 校验公共方法
 */
// 电话
const phoneReg = /^[0-9]{11}$/

// 必须为数字
const numberReg = /^\d+$|^\d+[.]?\d+$/

// 特殊字符
var specialKey = /[`～*~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/g

const regId = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/

const emailReg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/

const chineseReg = /[\u4E00-\u9FA5]/g
const FormValidate = (function() {
  function FormValidate() {}
  // From表单验证规则  可用于公用的校验部分
  FormValidate.Form = function() {
    return {

      // 只能数字的验证
      validateNumber(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (!numberReg.test(value)) {
            callback(new Error('只能填写数字'))
          } else {
            callback()
          }
        } else {
          callback()
        }
      },

      // 邮箱的验证规则
      validateEmail(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (!emailReg.test(value)) {
            callback(new Error('邮箱格式不正确'))
          } else {
            callback()
          }
        } else {
          callback()
        }
      },

      // 电话号码的验证
      validatePhone(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (!phoneReg.test(value)) {
            callback(new Error('手机格式不正确'))
          } else {
            callback()
          }
        }
      },

      // 身份证的验证规则
      ID(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (!regId.test(value)) {
            callback(new Error('请输入正确的二代身份证号码'))
          } else {
            callback()
          }
        }
      },
      // 不能输入特殊字符的验证规则
      validateSpecialKey(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (specialKey.test(value)) {
            callback(new Error('不能输入特殊字符'))
          } else {
            callback()
          }
        }
      },

      // 不能为中文的验证
      validateChinese(rule, value, callback) {
        if (value !== undefined && value !== '') {
          if (chineseReg.test(value)) {
            callback(new Error('不能填写中文'))
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
    }
  }

  return FormValidate
}())

exports.FormValidate = FormValidate
