<template>
  <div class="info-item">
    <p class="info-text">【<span style="color: #C31E18"><slot name="system">{{ system }}</slot></span>】<slot name="info">{{ info }}</slot></p>
    <p class="info-time"><slot name="time">{{ time }}</slot></p>
  </div>
</template>

<script>
export default {
  name: 'Infoitem',
  props: {
    system: {
      type: String,
      default: ''
    },
    info: {
      type: String,
      default: ''
    },
    time: {
      type: String,
      default: ''
    }
  },
  methods: {
    handleClick(evt) {
      this.$emit('click', evt)
    }
  }
}
</script>
<style scoped>
 .info-item{
   width: 100%;
   height: auto;
   padding: 5px;
   float: left;
   overflow: hidden;
   line-height: 16px;
   border-bottom: 1px dotted #dddddd;
   cursor: pointer;
 }
  .info-text{
    margin: 0;
    padding: 0;
    width: 100%;
    height: auto;
    overflow: hidden;
    font-size: 14px;
    line-height: 18px;
    color: #333333;
    text-align: left;
  }
  .info-time{
    margin: 0;
    padding: 0;
    width: 100%;
    height: auto;
    overflow: hidden;
    font-size: 12px;
    line-height: 16px;
    color: #AEAEAE;
    text-align: right;
  }
</style>
