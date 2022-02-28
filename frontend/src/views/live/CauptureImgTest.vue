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
      <div class="flex-row">
      <div class="source">
        <p>Canvas:</p>
        <vue-drawing-canvas
          ref="vueCanvasDrawing"
          v-model:image="image"
          :stroke-type="strokeType"
          :fill-shape="fillShape"
          :eraser="eraser"
          :lineWidth="line"
          :color="color"
          :background-color="backgroundColor"
          :background-image="captureImg"
          :watermark="watermark"
          saveAs="jpeg"
          :styles="{
            'border': 'solid 1px #000'
          }"
          :lock="disabled"
          @mousemove="getCoordinate($event)"
        />
        <div class="button-container">
          <button type="button" @click.prevent="$refs.vueCanvasDrawing.undo()">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-counterclockwise" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
              <path d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
            </svg>
            Undo
          </button>
          <button type="button" @click.prevent="$refs.vueCanvasDrawing.redo()">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-clockwise" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M8 3a5 5 0 1 0 4.546 2.914.5.5 0 0 1 .908-.417A6 6 0 1 1 8 2v1z"/>
              <path d="M8 4.466V.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384L8.41 4.658A.25.25 0 0 1 8 4.466z"/>
            </svg>
            Redo
          </button>
          <button type="button" @click.prevent="$refs.vueCanvasDrawing.redraw()">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-repeat" viewBox="0 0 16 16">
              <path d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z"/>
              <path fill-rule="evenodd" d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z"/>
            </svg>
            Refresh
          </button>
          <button type="button" @click.prevent="$refs.vueCanvasDrawing.reset()">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark" viewBox="0 0 16 16">
              <path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>
            </svg>
            Reset
          </button>
        </div>
        <div class="button-container">
          <button type="button" @click.prevent="eraser = !eraser">
            <span v-if="eraser">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eraser" viewBox="0 0 16 16">
                <path d="M8.086 2.207a2 2 0 0 1 2.828 0l3.879 3.879a2 2 0 0 1 0 2.828l-5.5 5.5A2 2 0 0 1 7.879 15H5.12a2 2 0 0 1-1.414-.586l-2.5-2.5a2 2 0 0 1 0-2.828l6.879-6.879zm2.121.707a1 1 0 0 0-1.414 0L4.16 7.547l5.293 5.293 4.633-4.633a1 1 0 0 0 0-1.414l-3.879-3.879zM8.746 13.547 3.453 8.254 1.914 9.793a1 1 0 0 0 0 1.414l2.5 2.5a1 1 0 0 0 .707.293H7.88a1 1 0 0 0 .707-.293l.16-.16z"/>
              </svg>
              Eraser
            </span>
            <span v-else>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
              </svg>
              Draw
            </span>
          </button>
          <select v-model="line">
            <option v-for="n in 25" :key="'option-' + n" :value="n">{{ n }}</option>
          </select>
          <input type="color" v-model="color">
          <select v-model="strokeType">
            <option value="dash">
              Dash
            </option>
            <option value="circle">
              Circle
            </option>
            <option value="square">
              Square
            </option>
            <option value="triangle">
              Triangle
            </option>
            <option value="half_triangle">
              Half Triangle
            </option>
          </select>
        </div>
      </div>
      </div>
  </div>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleClose">취소</el-button>
      <el-button @click="addimg" type="primary"
        >저장</el-button
      >
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, onMounted, ref} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import VueDrawingCanvas from 'vue-drawing-canvas';
import imageVue from '../room/components/board/image.vue';

export default {
  name: 'captureimgtest',
  components:{
    VueDrawingCanvas
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    captureImg:{
      type:String
    },
    roomId:{
      type:Number,
    }
  },
data() {
    return {
      x: 0,
      y: 0,
      image: '',
      eraser: false,
      disabled: false,
      fillShape: false,
      line: 5,
      color: '#000000',
      strokeType: 'dash',
      backgroundColor: '#FFFFFF',
      backgroundImage: computed(() => this.captureImg),
      watermark: null
    }
  },
   methods: {
    async setImage(event) {
      let URL = window.URL;
      this.backgroundImage = URL.createObjectURL(event.target.files[0]);
      await this.$refs.vueCanvasDrawing.redraw();
    },
    async setWatermarkImage(event) {
      let URL = window.URL;
      this.watermark = {
        type: 'Image',
        source: URL.createObjectURL(event.target.files[0]),
        x: 0,
        y: 0,
        imageStyle: {
          width: 600,
          height: 400
        }
      }
      await this.$refs.vueCanvasDrawing.redraw();
    },
    getCoordinate(event) {
      let coordinates = this.$refs.vueCanvasDrawing.getCoordinates(event);
      this.x = coordinates.x;
      this.y = coordinates.y;
    },
    addimg(){
      console.log(this.image);
      this.save(this.image);

    }
  },
  setup(props, { emit }) {
    const store = useStore();
    const vueCanvasDrawing = ref()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      captureImg: computed(()=> props.captureImg),
      isLogin: computed(() => store.getters['root/getJWTToken']),
      roomId: computed(()=> props.roomId),
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })
    const handleClose = function (){
      vueCanvasDrawing.value.reset()

      state.image = ''

      emit('closeCaptureImgDialog')
    }
    function dataURLtoFile(dataurl, filename) {

        var arr = dataurl.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]),
            n = bstr.length,
            u8arr = new Uint8Array(n);

        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }

        return new File([u8arr], filename, {type:mime});
    }
    const save = function(image){
      console.log(image);
       let formData = new FormData()
            var file = dataURLtoFile(image,"test.png");
            formData.append('upfile', file)
            formData.append('teamId', state.roomId)
            const today = new Date()
            const year = today.getFullYear()
            const month = ('0' + (today.getMonth() + 1)).slice(-2)
            const day = ('0' + today.getDate()).slice(-2)
            const dateString = `${year}-${month}-${day}`
            formData.append('uploadDate', dateString)
            console.log(formData);
            store.dispatch('root/uploadImage', { 'formData': formData, 'token': state.isLogin})
            .then(res => {
              setTimeout(() => {
                swal({
                  title: '팀 게시판 사진 등록',
                  text: '녹화된 사진이 팀 게시판에 등록 되었습니다.',
                  icon: 'success',
                  button: '확인',
                })
              }, 200)
              console.log(res)
            }).catch(err => {
              console.log(err)
            })

        handleClose();
    }





    return { state, handleClose, save, vueCanvasDrawing }
  }
}
</script>

<style>
  .capture-screen-share{
    position: relative;
    background-color: aliceblue;
    width: 650px !important;
    height: 650px !important;
    transform: translate(0, -20%);
  }

</style>
