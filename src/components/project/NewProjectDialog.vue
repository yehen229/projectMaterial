<template>
    <div>
      <h2>New Project Dialog</h2>
      <!-- Add your form elements and other UI components here -->
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from 'vue';
  
  export default defineComponent({
    name: 'NewProjectDialog',
    data() {
      return {
        projectName: '',
        projectDescription: ''
      };
    },
    methods: {
      submitForm() {
        console.log('Submit new project form');
      }
    }
  });
  </script>