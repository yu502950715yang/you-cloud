<template>
  <figure
    :class="'out-diag ' + iconShow"
  >

    <div class="in-box">
      <svg-icon v-if="iconShow==='iconShow'" :icon-class="iconClass" :icon-show="iconShow" />
      <p>
        <slot />
      </p>
    </div>

    <figcaption />
    <a @click="handleClick" />
  </figure>
</template>

<script>
export default {
  name: 'CoolBox',
  props: {
    iconClass: {
      type: String,
      default: 'wenjian'
    },
    iconShow: {
      type: String,
      default: 'iconShow'
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
  .out-diag {
    cursor: pointer;
    width: 100%;
    height: 100%;
    position: relative;
    display: inline-block;
    margin: 0px;
    max-width: 100%;
    background-color: #2277ff;
    color: #fff;
    overflow: hidden;
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    -moz-osx-font-smoothing: grayscale;
    -webkit-transform: translateZ(0);
    transform: translateZ(0);
  }

  .out-diag figcaption {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  }

  .yes:before,
  .yes:after,
  .yes {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-transition: all 0.35s ease;
    transition: all 0.35s ease;
  }

  .yes:before,
  .yes:after {
    top: 0;
    left: 0;
    background-color: transparent; /* 不支持线性的时候显示 */
    position: absolute;
    content: '';
    right: 0;
    bottom: 0;
  }

  .yes figcaption {
    opacity: 0;
    z-index: 1;
  }

  .yes:hover figcaption {
    outline: 2px solid #e3e3e3;
    opacity: 0.1;
    -webkit-transition-delay: 0.2s;
    transition-delay: 0.2s;
  }

  .yes:after,
  .yes:before {
    width: 200%;
    height: 200%;
    -webkit-transition: all 0.6s ease;
    transition: all 0.6s ease;
  }

  .yes:after {
    -webkit-transform: skew(-45deg) translateX(-150%);
    transform: skew(-45deg) translateX(-150%);
  }

  .yes:before {
    -webkit-transform: skew(-45deg) translateX(150%);
    transform: skew(-45deg) translateX(150%);
  }

  .yes:hover:after {
    -webkit-transform: skew(-45deg) translateX(-50%);
    transform: skew(-45deg) translateX(-50%);
  }

  .yes:hover:before {
    -webkit-transform: skew(-45deg) translateX(50%);
    transform: skew(-45deg) translateX(50%);
  }

  .no figcaption {
    cursor: not-allowed;
    background: #1f2d3d !important;
    opacity: 0.8
  }

  .no figcaption:after {
    text-align: center;
    width: 100px;
    height: 100px;
    display: inline-block;
    content: "无权限";
    position: absolute;
    line-height: 100px;
    top: 50%;
    margin-top: -50px;
    left: 50%;
    margin-left: -50px;
  }

  .iconShow > .in-box {
    display: block;
    top: 50%;
    margin-top: -50px;
    width: 100%;
    height: 100px;
    position: relative;
    text-align: center;
    color: #fff;
  }

  .iconShow > .in-box > svg {
    display: inline-block;
    width: 100%;
    height: 60px;
    background-size: cover;
  }

  .iconShow > .in-box > p {
    font-size: 26px;
    font-weight: 600;
    line-height: 40px;
    -webkit-margin-before: 0;
    -webkit-margin-after: 0;
  }

  .iconHide > .in-box {
    text-align: center;
    color: #fff;
  }

  .iconHide > .in-box > p {
    font-size: 26px;
    font-weight: 600;
  }

  .safe1 {
    background: #1b86db;
  }

  .safe2 {
    background: #75b25c;
  }

  .safe3 {
    background: #2fc0b2;
  }

  .safe4 {
    background: #dd8c06;
  }

  .safe5 {
    background: #d15d61;
  }

  .safe6 {
    background: #7a7fda;
  }

  .safe7 {
    background: #df592d;
  }

  .safe8 {
    background: #047f9d;
  }

  .safe9 {
    background: #b28f3a;
  }

  .safe10 {
    background: #CC6699;
  }

  .safe1:after, .safe1:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(47, 138, 203, 0.5));
  }

  .safe2:after, .safe2:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(149, 219, 123, 0.5));
  }

  .safe3:after, .safe3:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(53, 229, 215, 0.5));
  }

  .safe4:after, .safe4:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(243, 156, 6, 0.5));
  }

  .safe5:after, .safe5:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(232, 99, 103, 0.5));
  }

  .safe6:after, .safe6:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(137, 142, 238, 0.5));
  }

  .safe7:after, .safe7:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(249, 96, 45, 0.5));
  }

  .safe8:after, .safe8:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(4, 178, 216, 0.5));
  }

  .safe9:after, .safe9:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(228, 184, 74, 0.5));
  }

  .safe10:after, .safe10:before {
    background-image: linear-gradient(to bottom right, rgba(255, 255, 255, 0.1), rgba(249, 137, 198, 0.5));
  }
</style>
