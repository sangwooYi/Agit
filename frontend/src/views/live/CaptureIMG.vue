<template>
  <el-dialog
    custom-class="capture-screen-share"
    v-model="state.dialogVisible"
    @close="handleClose"
    title="Tips"
  >
  <template #title>
    <span>
      캡쳐 화면 저장하기
    </span>
  </template>

  <div>
      <img :src="state.captureImg" id="screenCapture" style="width:40vw !important; height:60vh !important;"/>
      <div>
        <button>저장</button>
      </div>
  </div>
</el-dialog>
</template>

<script>
import { reactive, computed, onMounted} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'ScreenShare',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    captureImg:{
      type:String
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      captureImg: computed(()=> props.captureImg)
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })
    const handleClose = function (){
      emit('closeCaptureImgDialog')
    }

    return { state, handleClose }
  }
}
</script>

<style>
  .capture-screen-share{
    position: relative;
    background-color: aliceblue;
    width: 43vw !important;
    height: 80vh !important;
    transform: translate(0, -20%);
  }

</style>
