<template>
  <el-scrollbar native>
    <div class="scrollbar-flex-content">
      <p v-for="item in state.srcList.length" :key="item" class="scrollbar-demo-image-item">
        <el-image
          style="width: 150px; height: 120px;"
          :src='state.srcList[item-1]'
          :preview-src-list="state.srcList"
          fit="fill"
          lazy
        >
        </el-image>
      </p>
    </div>
  </el-scrollbar>
</template>

<script>
import {onBeforeMount,computed,reactive} from 'vue'
import { useStore } from 'vuex'
export default {
  props:{
    uploaddate:{
      type:String
    },
    teamId:{
      type:Number
    },
    srcList:{
      type: Array,
    }
  },
  setup(props){
    const store = useStore()
    const state = reactive({
      size:'',
      srcList:computed(()=>props.srcList),
    })

    console.log(props.uploaddate);
    console.log(props.teamId);
    console.log(state.srcList);

    return {state};
  }
}
</script>

<style>
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
.scrollbar-flex-content {
  display: flex;
}
.scrollbar-demo-image-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 120px;
  margin: 1px 20px 1px 1px;
  text-align: center;
  border: 0px solid black;
  border-radius: 4px;
  background: transparent;
  color: var(--el-color-danger);
}


/* .is-horizontal {
  color: black;
} */
</style>
