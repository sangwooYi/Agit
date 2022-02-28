<template>
  <el-dialog
    custom-class="upload-video-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        동 영 상 추 가
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        동영상을 추가해보세요.
      </div>
      <div>
        <el-form :model="state.form" :rules="state.rules" ref="uploadVideoForm" :label-position="state.form.align">
          <el-form-item prop="schedule" label="일자">
            <el-date-picker
              style="width: 100%;"
              v-model="state.form.schedule"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
              disabled
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="content" label="내용">
          </el-form-item>
          <input
            id="uploadVideo"
            type="file"
            accept="video/*"
            @change="selectedVideo"
            ref="inputVideo"
          >
        </el-form>
      </div>
    </div>

    <!-- footer -->
    <template #footer>
      <span>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="uploadVideo">동영상등록</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
export default {
  name: 'create-schedule-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    },
     info:{
      type:Object,
      required:true,
    },
    registerDate: {
      type: String,
      default: '1970-01-01'
    }
  },

  setup(props, { emit }) {
    const uploadVideoForm = ref(null)
    const inputVideo = ref(null);
    const store = useStore();
    const state = reactive({
      form: {
        align: 'left',
        content: '',
        schedule: computed(() => props.registerDate),
        videos:[]
      },
      rules: {
        schedule: [
          { required: true, message: '날짜 선택은 필수입니다.', trigger: 'blur'}
        ],
        content: [
          { required: true, message: '내용 입력은 필수입니다.', trigger: 'blur'}
        ]
      },
      dialogVisible: computed(() => props.open),
      isLogin: computed(()=> store.getters['root/getJWTToken'])
    })

    const handleClose = function () {
      state.form.videos = []
      state.form.content = ''
      document.getElementById('uploadVideo').value = ''
      emit('closeUploadVideoDialog')
    }

    const selectedVideo = function(){
      console.log(inputVideo.value);
      for (let i = 0; i < inputVideo.value.files.length; i++) {
        console.log(state.form.videos)
        console.log(inputVideo.value.files[i])
        state.form.videos.push({
          file: inputVideo.value.files[i]
        })

      }
    }
    const uploadVideo = function () {
      console.log('비디오등록')
      let formData = new FormData();
        for (let i=0; i<state.form.videos.length; i++){
          formData.append('upfile', state.form.videos[i].file);
        }
        formData.append('uploadDate',state.form.schedule);
        formData.append('teamId',props.info)

        store.dispatch('root/uploadVideo',{ 'formData': formData, 'token': state.isLogin})
      .then(res => {
          setTimeout(() => {
                swal({
                  title: '비디오 등록',
                  text: '비디오가 일정에 등록되었습니다.',
                  icon: 'success',
                  button: '확인',
                });
              }, 500)

              // router.go(router.currentRoute)
              console.log(res)
              // console.log(swal)
              emit('createVideo')
              handleClose()
        })
        .catch(err => {
          console.log(err)
        })
      }

    return { state, handleClose, uploadVideoForm, uploadVideo , selectedVideo, inputVideo}
  }
}
</script>

<style>
.upload-video-dialog {
  width: 700px;
  height: 350px !important;
}
</style>
