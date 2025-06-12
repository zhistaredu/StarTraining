<template>
  <div class="circle-progress">
    <svg :style="{ width: size + 'px', height: size + 'px' }">
      <circle
        :cx="size / 2"
        :cy="size / 2"
        :r="(size - strokeWidth) / 2"
        :stroke-width="strokeWidth"
        :stroke="strokeColor"
        :fill="fillColor"
      />
    </svg>
  </div>
</template>

<script>
export default {
  data() {
    return {
      size: 100, // 圆形进度条的大小
      progress: 0, // 进度条的进度
      strokeWidth: 10, // 边界的宽度
      strokeColor: '#ccc', // 边界的颜色
      fillColor: 'transparent' // 填充的颜色
    }
  },
  methods: {
    // 更新进度条的进度
    updateProgress(newProgress) {
      if (newProgress >= 0 && newProgress <= 100) {
        this.progress = newProgress;
      }
    }
  }
}
</script>
