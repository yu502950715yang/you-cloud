<template>
  <div class="z-card">
    <slot />
  </div>
</template>

<script>
export default {
  name: 'ZCard'
}
</script>
<style scoped>
  .z-card{
    background-color: #fff;
    background-clip: border-box;
    border: 1px solid #e7eaed;
    padding: 15px;
    margin-bottom: 15px;
    border-radius: 2px;
  }
</style>
