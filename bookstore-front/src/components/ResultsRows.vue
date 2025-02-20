<template>
    <span>
      <span v-if="total === 0">找不到書本</span>
      <span v-else>查到 {{ total }} 本書</span>，每頁顯示
      <select :value="modelValue" @change="handleChange">
        <option v-for="choice in choices" :key="choice" :value="choice">
          {{ choice }}
        </option>
      </select>
      本書
    </span>
  </template>
  
  <script setup>
  const props = defineProps(["modelValue", "choices", "total"]);
  const emits = defineEmits(["update:modelValue", "change"]);
  
  function handleChange(event) {
    const newValue = parseInt(event.target.value, 10);
    emits("update:modelValue", newValue);
    emits("change", newValue);
  }
  </script>
  